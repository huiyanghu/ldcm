package com.lvdun.service.impl;

import com.lvdun.dao.BaseLdUserRepository;
import com.lvdun.dao.CmAccountRepository;
import com.lvdun.dao.CmCustumerRepository;
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
    CmCustumerRepository custumerDao;
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
                map.put("customId", account.getCustumerId());
                CmCustomer custumer = custumerDao.findOne(account.getCustumerId());
                map.put("platformId", custumer.getPlatformId());
                return map;
            }
        }
        return null;
    }

    @Override
    public Integer checkIsExists(String username) {
        List<CmAccount> accountList = accountDao.getByAccount(username);
        if (accountList != null && accountList.size() > 0) {
            return accountList.size();
        }
        return 0;
    }


    @Override
    @Transactional
    public CmAccount register(String email, String companyName, String name, String mobile, String password) {
        /*平台*/
        CodePlatform platform = new CodePlatform();
        platform.setStatus(0);
        platformDao.save(platform);

        /*客户(公司)*/
        CmCustomer custumer = new CmCustomer();
        custumer.setCustomerName(companyName);
        custumer.setContactsMobile(mobile);
        custumer.setStatus(0);
        custumer.setPlatformId(platform.getId());
        custumerDao.save(custumer);



        /*账户*/
        CmAccount account = new CmAccount();
        account.setAccount(email);
        account.setName(name);
        account.setEmail(email);
        account.setMobile(mobile);
        account.setPassword(MD5.MD5(password));
        account.setStatus(0);//0新注册用户（未经过超级管理员审核）-1、体验用户2、正式收费用户3、正式免费用户4、停用
        account.setCustumerId(custumer.getId());
        CmAccount cmAccount = accountDao.save(account);

        /*运营人员*/
        BaseLdUser baseLdUser = new BaseLdUser();
        baseLdUser.setCustomerId(custumer.getId());
        baseLdUser.setAccount(account);
        baseLdUser.setEmail(email);
        baseLdUser.setMobile(mobile);
        baseLdUser.setName(name);
        baseLdUserDao.save(baseLdUser);

        return cmAccount;
    }


}
