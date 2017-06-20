package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.service.BaseLdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
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

        Map resutltMap = new HashMap();
        int isSuccess = 0;
        Map result = new HashMap();

        try {
            result = baseLdUserService.getBaseLdUserPage(customerId, page, pageSize);
            isSuccess = 1;
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }

        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }
}
