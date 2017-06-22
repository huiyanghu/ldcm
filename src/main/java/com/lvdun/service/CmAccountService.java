package com.lvdun.service;

import com.lvdun.entity.CmAccount;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/14.
 */
public interface CmAccountService {
    public Map login(String username, String password);

    public Boolean checkAccountIsExists(String username);
    public Boolean checkMobileIsExists(String username);

    public CmAccount register(String email, String companyName, String name, String mobile, String password);
}
