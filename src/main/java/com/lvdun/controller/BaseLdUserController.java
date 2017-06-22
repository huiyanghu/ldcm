package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.service.BaseLdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(path = "/addBaseLdUser", method = RequestMethod.POST)
    @ResponseBody
    public Object addBaseLdUser(HttpSession session, String email, String name, String mobile, Integer roleFlag, String password, String verificationCode) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long customerId = Long.parseLong("" + loginUser.get("customerId"));
        Long customerId = 1L;

        Map resutltMap = new HashMap();
        int isSuccess = 0;
        int code = 0;////code:0//失败  1//成功  2//验证码
        Map result = new HashMap();

        verificationCode = verificationCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!verificationCode.equals(verCode)) {
            code = 2;
            isSuccess = 0;
        } else {
            try {
                baseLdUserService.addUser(customerId, email, name, mobile, roleFlag, password);
                code = 1;
                isSuccess = 1;
            } catch (Exception e) {
                code = 0;
                isSuccess = 0;
                e.printStackTrace();
            }
        }


        resutltMap.put("isSuccess", isSuccess);
        result.put("code", code);
        resutltMap.put("result", result);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/updateBaseLdUser", method = RequestMethod.POST)
    @ResponseBody
    public Object updateBaseLdUser(HttpSession session, Long baseLdUserId, String name, String mobile, Integer roleFlag, String verificationCode) {

        Map resutltMap = new HashMap();
        int isSuccess = 0;
        int code = 0;////code:0//失败  1//成功  2//验证码
        Map result = new HashMap();

        verificationCode = verificationCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!verificationCode.equals(verCode)) {
            code = 2;
            isSuccess = 0;
        } else {
            try {
                baseLdUserService.updateUser(baseLdUserId, name, mobile, roleFlag);
                code = 1;
                isSuccess = 1;
            } catch (Exception e) {
                code = 0;
                isSuccess = 0;
                e.printStackTrace();
            }
        }

        resutltMap.put("isSuccess", isSuccess);
        result.put("code", code);
        resutltMap.put("result", result);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/deleteBaseLdUser", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object deleteBaseLdUser(HttpSession session, Long baseLdUserId) {

        Map resutltMap = new HashMap();
        int isSuccess = 0;

        try {
            baseLdUserService.deleteUser(baseLdUserId);
            isSuccess = 1;
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }

        resutltMap.put("isSuccess", isSuccess);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/changeBaseLdUserRole", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object changeBaseLdUserRole(HttpSession session, Long baseLdUserId) {

        Map resutltMap = new HashMap();
        int isSuccess = 0;

        try {
            baseLdUserService.changeBaseLdUserRole(baseLdUserId);
            isSuccess = 1;
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }

        resutltMap.put("isSuccess", isSuccess);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }
}
