package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.service.BaseLdUserService;
import com.lvdun.service.CmAccountService;
import com.lvdun.util.StringUtil;
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
    @Autowired
    CmAccountService accountService;


    @RequestMapping("/list")
    @ResponseBody
    public Object list(HttpSession session, @RequestParam(required = false, name = "page", defaultValue = "1") Integer page, @RequestParam(required = false, name = "pageSize", defaultValue = "10") Integer pageSize) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        Long customerId = Long.parseLong("" + loginUser.get("customerId"));
        //Long customerId = 1L;

        Map resutltMap = new HashMap();
        int isSuccess = 1;
        Map result = new HashMap();
        try {
            result = baseLdUserService.getBaseLdUserPage(customerId, page, pageSize);
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }

        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(HttpSession session, String email, String name, String mobile, Integer roleFlag, String password, String verificationCode) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        Long customerId = Long.parseLong("" + loginUser.get("customerId"));
        //Long customerId = 1L;

        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 1;
        int code = -1;////code:0//失败  1//成功  2//验证码

        if (StringUtil.isEmpty(email)) {
            result.put("code", 1);
            resutltMap.put("isSuccess", 1);
            resutltMap.put("result", result);
            return JSON.toJSON(resutltMap);
        }
        if (StringUtil.isEmpty(name)) {
            result.put("code", 3);
            resutltMap.put("isSuccess", 1);
            resutltMap.put("result", result);
            return JSON.toJSON(resutltMap);
        }
        if (StringUtil.isEmpty(mobile)) {
            result.put("code", 4);
            resutltMap.put("isSuccess", 1);
            resutltMap.put("result", result);
            return JSON.toJSON(resutltMap);
        }
        if (StringUtil.isEmpty(password)) {
            result.put("code", 5);
            resutltMap.put("isSuccess", 1);
            resutltMap.put("result", result);
            return JSON.toJSON(resutltMap);
        }


        verificationCode = verificationCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!verificationCode.equals(verCode)) {
            code = 2;
        } else {
            try {
                if (accountService.checkMobileIsExists(mobile)) {
                    code = 9;//手机号已存在
                } else {
                    if (accountService.checkAccountIsExists(email)) {
                        code = 7;
                    } else {
                        baseLdUserService.addUser(customerId, email, name, mobile, roleFlag, password);
                        code = -1;
                    }
                }
            } catch (Exception e) {
                isSuccess = 0;
                e.printStackTrace();
            }
        }

        resutltMap.put("isSuccess", isSuccess);
        result.put("code", code);
        resutltMap.put("result", result);
        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }


    @RequestMapping(path = "/getUserDetail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object getUserDetail(HttpSession session, @RequestParam(name = "accountId", required = false) Long accountId) {

        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 1;
        if (StringUtil.isEmpty(accountId)) {
            Map loginUser = (Map) session.getAttribute("loginUser");
            accountId = Long.parseLong("" + loginUser.get("id"));
        }

        try {
            Map userDetail = baseLdUserService.getUserDetail(accountId);
            result.put("userDetail", userDetail);
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }


        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);

    }


    @RequestMapping(path = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Object updateBaseLdUser(HttpSession session, @RequestParam(required = false, name = "accountId") Long accountId, String name, String mobile, Integer roleFlag, String verificationCode, @RequestParam(required = false, name = "newPassword") String newPassword) {

        Map resutltMap = new HashMap();
        int isSuccess = 1;
        int code = -1;////code:0//失败  1//成功  2//验证码
        Map result = new HashMap();

        if (StringUtil.isEmpty(accountId)) {
            Map loginUser = (Map) session.getAttribute("loginUser");
            accountId = Long.parseLong("" + loginUser.get("id"));
        }

        verificationCode = verificationCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!verificationCode.equals(verCode)) {
            code = 2;
        } else {
            try {
                baseLdUserService.updateUser(accountId, name, mobile, roleFlag, newPassword);
            } catch (Exception e) {
                isSuccess = 0;
                e.printStackTrace();
            }
        }

        resutltMap.put("isSuccess", isSuccess);
        result.put("code", code);
        resutltMap.put("result", result);
        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/deleteUser", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object deleteBaseLdUser(HttpSession session, Long baseLdUserId) {

        Map resutltMap = new HashMap();
        int isSuccess = 1;

        try {
            baseLdUserService.deleteUser(baseLdUserId);
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }

        resutltMap.put("isSuccess", isSuccess);
        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/changeBaseLdUserRole", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object changeBaseLdUserRole(HttpSession session, Long baseLdUserId) {

        Map resutltMap = new HashMap();
        int isSuccess = 1;

        try {
            baseLdUserService.changeBaseLdUserRole(baseLdUserId);
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }

        resutltMap.put("isSuccess", isSuccess);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/userManagement")
    public String userManagement() {
        return "user-management/user-management";
    }

    @RequestMapping("/addEditUser")
    public String addEditUser(Map map, String flag, String dataJson) {
        map.put("flag", flag);
        map.put("dataJson", dataJson);
        return "user-management/add-edit-user";
    }

}
