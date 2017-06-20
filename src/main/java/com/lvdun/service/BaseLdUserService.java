package com.lvdun.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/20.
 */
public interface BaseLdUserService {
    public Map getBaseLdUserPage(Long customerId, Integer page, Integer pageSize);
}
