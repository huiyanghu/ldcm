package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.dao.UserRepository;
import com.lvdun.entity.User;
import com.lvdun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */
@Controller
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/getUserById")
    public String getUserById(Long id) {
        User u = userService.findOne(id);
        System.out.println(u);
        return "hello/list";
    }

    @RequestMapping("/saveUser")
    public String saveUser() {
        User u = new User();
        u.setUserName("wan");
        u.setUserAddress("江西省上饶市鄱阳县");
        u.setUserAge(100);
        userService.save(u);
        return "hello/list";
    }

    @RequestMapping("/testSql")
    public String testSql() {
        List<User> list = userRepository.test();

        System.out.println(JSON.toJSONString(list));
        for (User u : list) {
            System.out.println(u.getDepartment().getName());
        }
        return "hello/list";
    }
}
