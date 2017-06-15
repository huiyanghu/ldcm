package com.lvdun.service.impl;

import com.lvdun.dao.CmAccountDao;
import com.lvdun.entity.CmAccount;
import com.lvdun.service.CmAccountService;
import com.lvdun.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14.
 */
@Service
public class CmAccountServiceImpl implements CmAccountService {
    @Autowired
    CmAccountDao accountDao;

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
}
