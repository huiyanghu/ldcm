package com.lvdun.service.impl;

import com.lvdun.dao.*;
import com.lvdun.entity.BaseLdUser;
import com.lvdun.entity.CmCustomer;
import com.lvdun.entity.CodeApp;
import com.lvdun.entity.CodePlatform;
import com.lvdun.service.CustomerService;
import com.lvdun.util.ConstantsUtil;
import com.lvdun.util.DateUtil;
import com.lvdun.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Administrator on 2017/6/19.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CmCustomerRepository customerDao;
    @Autowired
    CodePlatformRepository platformDao;
    @Autowired
    BaseLdUserRepository baseLdUserDao;
    @Autowired
    CodeAppRepository appDao;
    @Autowired
    CmAccountRepository accountDao;


    @Override
    public Boolean checkIsExists(String customerName) {
        List<CmCustomer> customerList = customerDao.getByCustomerName(customerName);
        if (customerList != null && customerList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Map getCustomerInfo(Long customerId) {
        CmCustomer customer = customerDao.findOne(customerId);
        CodePlatform platform = platformDao.findOne(customer.getPlatformId());
        List<BaseLdUser> baseLdUserList = baseLdUserDao.getPrimeBaseLdUser(customerId);

        BaseLdUser baseLdUser = null;
        if (baseLdUserList != null && !baseLdUserList.isEmpty()) {
            baseLdUser = baseLdUserList.get(0);
            baseLdUser.setAccount(null);
        }

        List<CodeApp> appList = appDao.getCodeAppByPlatformId(customer.getPlatformId());

        Map map = new HashMap();
        map.put("basicInfo", customer);
        map.put("commonInfo", platform);
        map.put("operateInfo", baseLdUser);
        map.put("appList", appList);
        return map;
    }

    @Override
    @Transactional
    public void updateCustomerBasicInfo(Long customerId, String customerName, String contactsName, String contactsMobile, String approvalTime, String province, String city, String region) {
        CmCustomer customer = customerDao.findOne(customerId);
        customer.setCustomerName(customerName);
        customer.setContactsName(contactsName);
        customer.setContactsMobile(contactsMobile);
        customer.setApprovalTime(StringUtil.isEmpty(approvalTime) ? null : DateUtil.getDateByStr(approvalTime));
        customer.setProvince(province);
        customer.setCity(city);
        customer.setRegion(region);

        customerDao.save(customer);
    }

    @Override
    public Map getCustomerPage(Integer page, Integer pageSize) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, pageSize, sort);
        Page<CmCustomer> cmCustomerPage = customerDao.findAll(pageable);
        List<CmCustomer> customerList = cmCustomerPage.getContent();
        Map result = new HashMap();
        List<Map> list = new ArrayList<>();

        for (CmCustomer customer : customerList) {
            Map map = new HashMap();
            //List<CmAccount> accountList=accountDao.getPrimeCmAccount(customer.getId());
            List<BaseLdUser> baseLdUserList = baseLdUserDao.getPrimeBaseLdUser(customer.getId());
            map.put("customerName", customer.getCustomerName());
            map.put("aprovalTime", DateUtil.formatDate(customer.getApprovalTime()));
            map.put("status", customer.getStatus());
            map.put("statusStr", ConstantsUtil.getConstants("CmCustomer_status", "" + customer.getStatus()));
            if (baseLdUserList != null && !baseLdUserList.isEmpty()) {
                BaseLdUser baseLdUser = baseLdUserList.get(0);
                map.put("email", baseLdUser.getEmail());
                map.put("name", baseLdUser.getName());
            }
            list.add(map);
        }
        result.put("content", list);
        result.put("total", cmCustomerPage.getTotalElements());
        result.put("pageCount", cmCustomerPage.getTotalPages());
        result.put("currentPage", cmCustomerPage.getNumber() + 1);
        result.put("pageSize", cmCustomerPage.getSize());

        return result;
    }

    @Override
    public void reviewCustomer(Long customerId) {
        CmCustomer customer = customerDao.getOne(customerId);
        customer.setApprovalTime(new Date());
        customer.setStatus(1);
        customerDao.save(customer);
    }
}
