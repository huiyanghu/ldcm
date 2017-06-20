package com.lvdun.service.impl;

import com.lvdun.dao.BaseLdUserRepository;
import com.lvdun.dao.CmCustumerRepository;
import com.lvdun.dao.CodeAppRepository;
import com.lvdun.dao.CodePlatformRepository;
import com.lvdun.entity.BaseLdUser;
import com.lvdun.entity.CmCustomer;
import com.lvdun.entity.CodeApp;
import com.lvdun.entity.CodePlatform;
import com.lvdun.service.CustomerService;
import com.lvdun.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/19.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CmCustumerRepository custumerDao;
    @Autowired
    CodePlatformRepository platformDao;
    @Autowired
    BaseLdUserRepository baseLdUserDao;
    @Autowired
    CodeAppRepository appDao;


    @Override
    public Map getCustomerInfo(Long customerId) {
        CmCustomer custumer = custumerDao.findOne(customerId);
        CodePlatform platform = platformDao.findOne(custumer.getPlatformId());
        List<BaseLdUser> baseLdUserList = baseLdUserDao.getPrimeBaseLdUser(customerId);

        BaseLdUser baseLdUser = null;
        if (baseLdUserList != null && !baseLdUserList.isEmpty()) {
            baseLdUser = baseLdUserList.get(0);
        }

        List<CodeApp> appList = appDao.getCodeAppByPlatformId(custumer.getPlatformId());

        Map map = new HashMap();
        map.put("basicInfo", custumer);
        map.put("commonInfo", platform);
        map.put("operateInfo", baseLdUser);
        map.put("appList", appList);
        return map;
    }

    @Override
    public void updateCustomerBasicInfo(Long customerId, String customerName, String contactsName, String contactsMobile, String approvalTime, String province, String city, String region) {
        CmCustomer customer = custumerDao.findOne(customerId);
        customer.setCustomerName(customerName);
        customer.setContactsName(contactsName);
        customer.setContactsMobile(contactsMobile);
        customer.setApprovalTime(DateUtil.getDateByStr(approvalTime));
        customer.setProvince(province);
        customer.setCity(city);
        customer.setRegion(region);

        custumerDao.save(customer);
    }
}
