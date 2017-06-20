package com.lvdun.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/20.
 */
public interface BaseLdUserService {
    public Map getBaseLdUserPage(Long customerId, Integer page, Integer pageSize);

    public void addUser(Long customerId, String email, String name, String mobile, Integer roleFlag, String password);

    public void updateUser(Long baseLdUserId, String name, String mobile, Integer roleFlag);

    public void deleteUser(Long baseLdUserId);

    public void changeBaseLdUserRole(Long baseLdUserId);


}
