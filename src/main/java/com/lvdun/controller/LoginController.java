package com.lvdun.controller;

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

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password, String checkcode, RedirectAttributes attributes) {
        if (StringUtil.isEmpty(checkcode)) {
            attributes.addFlashAttribute("msg", MsgConstants.EMPTY_VERCODE);
            return "redirect:/toLogin";
        }
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            attributes.addFlashAttribute("msg", MsgConstants.EMPTY_USERNAME_PASSWORD);
            return "redirect:/toLogin";
        }
        checkcode = checkcode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!checkcode.equals(verCode)) {
            attributes.addFlashAttribute("msg", MsgConstants.WRONG_VERCODE);
            return "redirect:/toLogin";
        } else {
            Map map = accountService.login(username, password);
            if (map != null) {
                session.setAttribute("loginUser", map);
                return "redirect:/";
            }else{
                attributes.addFlashAttribute("msg", MsgConstants.WRONG_USERNAME_PASSWORD);
                return "redirect:/toLogin";
            }
        }

    }
}
