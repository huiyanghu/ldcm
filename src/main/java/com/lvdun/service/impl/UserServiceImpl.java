package com.lvdun.service.impl;

import com.lvdun.dao.UserRepository;
import com.lvdun.entity.User;
import com.lvdun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/8.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public User findOne(Long id) {
        User user = userRepository.findOne(id);
        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }


}
