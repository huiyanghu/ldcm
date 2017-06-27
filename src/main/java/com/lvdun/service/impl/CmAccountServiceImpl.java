package com.lvdun.service.impl;

import com.lvdun.dao.BaseLdUserRepository;
import com.lvdun.dao.CmAccountRepository;
import com.lvdun.dao.CmCustomerRepository;
import com.lvdun.dao.CodePlatformRepository;
import com.lvdun.entity.BaseLdUser;
import com.lvdun.entity.CmAccount;
import com.lvdun.entity.CmCustomer;
import com.lvdun.entity.CodePlatform;
import com.lvdun.service.CmAccountService;
import com.lvdun.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14.
 */
@Service
public class CmAccountServiceImpl implements CmAccountService {
    @Autowired
    CmAccountRepository accountDao;
    @Autowired
    CmCustomerRepository customerDao;
    @Autowired
    CodePlatformRepository platformDao;
    @Autowired
    BaseLdUserRepository baseLdUserDao;


    @Override
    public Map login(String username, String password) {

        List<CmAccount> accountList = accountDao.getByAccount(username);
        if (accountList != null && accountList.size() > 0) {
            CmAccount account = accountList.get(0);
            if (account.getPassword().equals(MD5.MD5(password).toUpperCase())) {
                Map map = new HashMap();
                map.put("id", account.getId());
                map.put("name", account.getName());
                map.put("account", account.getAccount());
                map.put("customId", account.getCustomerId());
                CmCustomer customer = customerDao.findOne(account.getCustomerId());
                map.put("platformId", customer.getPlatformId());
                map.put("roleFlag", account.getRoleFlag());
                return map;
            }
        }
        return null;
    }

    @Override
    public Boolean checkAccountIsExists(String acccount) {
        List<CmAccount> accountList = accountDao.getByAccount(acccount);
        if (accountList != null && accountList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkMobileIsExists(String mobile) {
        List<CmAccount> accountList = accountDao.getByMobile(mobile);
        if (accountList != null && accountList.size() > 0) {
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public CmAccount register(String email, String companyName, String name, String mobile, String password) {
        /*平台*/
        CodePlatform platform = new CodePlatform();
        platform.setStatus(0);
        platformDao.save(platform);

        /*客户(公司)*/
        CmCustomer customer = new CmCustomer();
        customer.setCustomerName(companyName);
        customer.setContactsMobile(mobile);
        customer.setStatus(0);
        customer.setPlatformId(platform.getId());
        customerDao.save(customer);

        /*账户*/
        CmAccount account = new CmAccount();
        account.setAccount(email);
        account.setName(name);
        account.setEmail(email);
        account.setRoleFlag(1);//管理员
        account.setMobile(mobile);
        account.setPassword(MD5.MD5(password).toUpperCase());
        account.setStatus(0);//0新注册用户（未经过超级管理员审核）-1、体验用户2、正式收费用户3、正式免费用户4、停用
        account.setCustomerId(customer.getId());
        CmAccount cmAccount = accountDao.save(account);

        /*运营人员*/
        BaseLdUser baseLdUser = new BaseLdUser();
        baseLdUser.setCustomerId(customer.getId());
        baseLdUser.setAccount(account);
        baseLdUser.setEmail(email);
        baseLdUser.setMobile(mobile);
        baseLdUser.setName(name);
        baseLdUserDao.save(baseLdUser);

        return cmAccount;
    }

    @Override
    public CmAccount getByAccount(String email) {
        List<CmAccount> accountList = accountDao.getByAccount(email);
        if (accountList != null && !accountList.isEmpty()) {
            return accountList.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public void save(CmAccount account) {
        accountDao.save(account);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String newPassword) {
        CmAccount account = accountDao.findOne(id);
        account.setPassword(MD5.MD5(newPassword).toUpperCase());
        String activityCodeNew = "" + (int) ((Math.random() * 9 + 1) * 100000);
        accountDao.save(account);
    }


}
