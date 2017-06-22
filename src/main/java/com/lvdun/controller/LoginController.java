package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.entity.CmAccount;
import com.lvdun.service.CmAccountService;
import com.lvdun.service.CustomerService;
import com.lvdun.util.MsgConstants;
import com.lvdun.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14.
 */
@Controller
public class LoginController {
    @Autowired
    CmAccountService accountService;
    @Autowired
    CustomerService customerService;

    @RequestMapping("/")
    public String toIndex() {
        System.out.println("===want to index");
        return "index";
    }

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password, String verificationCode, RedirectAttributes attributes) {
        if (StringUtil.isEmpty(verificationCode)) {
            attributes.addFlashAttribute("msg", MsgConstants.EMPTY_VERCODE);
            return "redirect:/toLogin";
        }
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            attributes.addFlashAttribute("msg", MsgConstants.EMPTY_USERNAME_PASSWORD);
            return "redirect:/toLogin";
        }
        verificationCode = verificationCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!verificationCode.equals(verCode)) {
            attributes.addFlashAttribute("msg", MsgConstants.WRONG_VERCODE);
            return "redirect:/toLogin";
        } else {
            Map map = accountService.login(username, password);
            if (map != null) {
                session.setAttribute("loginUser", map);
                return "redirect:/";
            } else {
                attributes.addFlashAttribute("msg", MsgConstants.WRONG_USERNAME_PASSWORD);
                return "redirect:/toLogin";
            }
        }

    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)

    public String register(HttpSession session, String email, String companyName, String name, String mobile, String password, String confirmPassword, String verificationCode, RedirectAttributes attributes) {
        if (!password.equals(confirmPassword)) {
            attributes.addFlashAttribute("msg", MsgConstants.WRONG_VERCODE);
            return "redirect:/toRegister";
        }
        verificationCode = verificationCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!verificationCode.equals(verCode)) {
            attributes.addFlashAttribute("msg", MsgConstants.WRONG_VERCODE);
            return "redirect:/toRegister";
        } else {

            CmAccount cmAccount = accountService.register(email, companyName, name, mobile, password);
            attributes.addFlashAttribute("msg", MsgConstants.REGISTER_SUCCESS);
            return "redirect:/toLogin";
        }


    }

    @RequestMapping(path = "/loginAjax", method = RequestMethod.POST)
    @ResponseBody
    public Object loginAjax(HttpSession session, String username, String password, String verificationCode) {
        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 0;
        int code = -1;////code # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在
        if (StringUtil.isEmpty(username)) {
            result.put("code", 1);
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
            code = 0;
            isSuccess = 1;
        } else {
            Map map = null;
            try {
                System.out.println("username  : " + username);
                map = accountService.login(username, password);
                if (map != null) {
                    code = -1;
                    session.setAttribute("loginUser", map);
                    isSuccess = 1;
                } else {
                    code = 10;
                    isSuccess = 1;
                }


            } catch (Exception e) {
                e.printStackTrace();
                isSuccess = 0;
            }
        }
        result.put("code", code);
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/registerAjax", method = RequestMethod.POST)
    @ResponseBody
    public Object registerAjax(HttpSession session, String email, String companyName, String name, String mobile, String password, String confirmPassword, String verificationCode) {

        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 0;
        int code = -1;////code # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在

        if (StringUtil.isEmpty(email)) {
            result.put("code", 1);
            resutltMap.put("isSuccess", 1);
            resutltMap.put("result", result);
            return JSON.toJSON(resutltMap);
        }
        if (StringUtil.isEmpty(companyName)) {
            result.put("code", 2);
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

        if (!password.equals(confirmPassword)) {
            result.put("code", 6);
            resutltMap.put("isSuccess", 1);
            resutltMap.put("result", result);
            return JSON.toJSON(resutltMap);
        }


        verificationCode = verificationCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!verificationCode.equals(verCode)) {
            code = 2;
            isSuccess = 1;
        } else {
            try {
                if (customerService.checkIsExists(companyName)) {
                    code = 8;
                    isSuccess = 1;
                } else {
                    if (accountService.checkIsExists(email)) {
                        code = 7;
                        isSuccess = 1;
                    } else {
                        accountService.register(email, companyName, name, mobile, password);
                        code = -1;
                        isSuccess = 1;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                isSuccess = 0;
            }
        }

        result.put("code", code);
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/sendMessage")
    @ResponseBody
    public Object sendMessage(String mobile) {

        return null;
    }

    @RequestMapping(path = "/checkByMobile", method = RequestMethod.POST)
    @ResponseBody
    public Object registerAjax(HttpSession session, String email, String phone, String msgCode) {

        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 0;
        int code = -1;////code # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在



         {
            try {

            } catch (Exception e) {
                e.printStackTrace();
                isSuccess = 0;
            }
        }

        result.put("code", code);
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        return JSON.toJSON(resutltMap);
    }




    @RequestMapping("/test")
    public String test() {
        return "hello/test";
    }
}
