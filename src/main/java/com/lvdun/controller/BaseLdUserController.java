package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.service.BaseLdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/20.
 */
@Controller
@RequestMapping("/baseLdUser")
public class BaseLdUserController {
    @Autowired
    BaseLdUserService baseLdUserService;

    @RequestMapping("/list")
    @ResponseBody
    public Object list(HttpSession session, @RequestParam(required = false, name = "page", defaultValue = "1") Integer page, @RequestParam(required = false, name = "pageSize", defaultValue = "10") Integer pageSize) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long customerId = Long.parseLong("" + loginUser.get("customerId"));
        Long customerId = 1L;

        Map result = baseLdUserService.getBaseLdUserPage(customerId, page, pageSize);
        System.out.println(JSON.toJSONString(result));
        return JSON.toJSON(result);
    }
}
