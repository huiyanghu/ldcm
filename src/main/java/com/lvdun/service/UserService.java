package com.lvdun.service;


import com.lvdun.entity.User;

/**
 * Created by Administrator on 2017/6/8.
 */
public interface UserService {
    public User findOne(Long id);

    public void save(User user);
}
