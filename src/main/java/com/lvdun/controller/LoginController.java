package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.entity.CmAccount;
import com.lvdun.service.CmAccountService;
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

    @RequestMapping(path = "/registerAjax", method = RequestMethod.POST)
    @ResponseBody
    public Object registerAjax(HttpSession session, String email, String companyName, String name, String mobile, String password, String confirmPassword, String verificationCode) {
        //code:0//失败  1//成功  2//验证码
        Map resutltMap = new HashMap();
        Map result = new HashMap();

        if (!password.equals(confirmPassword)) {
            result.put("code", 0);
            resutltMap.put("isSuccess", 1);
            resutltMap.put("result", result);
            return JSON.toJSON(resutltMap);
        }
        verificationCode = verificationCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!verificationCode.equals(verCode)) {
            result.put("code", 2);
            resutltMap.put("isSuccess", 1);
            resutltMap.put("result", result);
            return JSON.toJSON(resutltMap);
        } else {
            try {
                accountService.register(email, companyName, name, mobile, password);
                result.put("code", 1);
                resutltMap.put("isSuccess", 1);
                resutltMap.put("result", result);
                return JSON.toJSON(resutltMap);
            } catch (Exception e) {
                e.printStackTrace();
                result.put("code", 0);
                resutltMap.put("isSuccess", 0);
                resutltMap.put("result", result);
                return JSON.toJSON(resutltMap);
            }

        }


    }
}
