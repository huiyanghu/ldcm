package com.lvdun.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/14.
 */
public interface CmAccountService {
    public Map login(String username,String password);
    public Integer checkIsExists(String username);
}
