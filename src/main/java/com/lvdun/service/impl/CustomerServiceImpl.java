package com.lvdun.service.impl;

import com.lvdun.dao.BaseLdUserRepository;
import com.lvdun.dao.CmCustumerRepository;
import com.lvdun.dao.CodeAppRepository;
import com.lvdun.dao.CodePlatformRepository;
import com.lvdun.entity.BaseLdUser;
import com.lvdun.entity.CmCustumer;
import com.lvdun.entity.CodeApp;
import com.lvdun.entity.CodePlatform;
import com.lvdun.service.CustomerService;
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
    public Map getCustomerInfo(Long custumerId) {
        CmCustumer custumer = custumerDao.findOne(custumerId);
        CodePlatform platform = platformDao.findOne(custumer.getPlatformId());
        List<BaseLdUser> baseLdUserList = baseLdUserDao.getPrimeBaseLdUser(custumerId);

        BaseLdUser baseLdUser = null;
        if (baseLdUserList != null && !baseLdUserList.isEmpty()) {
            baseLdUser = baseLdUserList.get(0);
        }

        List<CodeApp> appList=appDao.getCodeAppByPlatformId(custumer.getPlatformId());

        Map map = new HashMap();
        map.put("basicInfo", custumer);
        map.put("commonInfo", platform);
        map.put("operateInfo", baseLdUser);
        map.put("appList",appList);
        return map;
    }
}
