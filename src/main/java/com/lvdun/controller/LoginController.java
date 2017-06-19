package com.lvdun.controller;

import com.lvdun.entity.CmAccount;
import com.lvdun.service.CmAccountService;
import com.lvdun.util.MsgConstants;
import com.lvdun.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
}
