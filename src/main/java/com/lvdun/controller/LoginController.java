package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.entity.CmAccount;
import com.lvdun.service.CmAccountService;
import com.lvdun.service.CustomerService;
import com.lvdun.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
        int isSuccess = 1;
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
        } else {
            try {
                if (accountService.checkMobileIsExists(mobile)) {
                    code = 9;//手机号已存在
                } else {
                    if (customerService.checkCustomerNameIsExists(companyName)) {
                        code = 8;
                    } else {
                        if (accountService.checkAccountIsExists(email)) {
                            code = 7;
                        } else {
                            accountService.register(email, companyName, name, mobile, password);
                            code = -1;
                        }
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

    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePassword(HttpSession session, @RequestParam(name = "email",required = false) Long id, String newPassword) {
        Map resutltMap = new HashMap();
        int isSuccess = 1;
        if (StringUtil.isEmpty(id)) {//修改自己的密码
            Map loginUser = (Map) session.getAttribute("loginUser");
            id = Long.parseLong(""+loginUser.get("id"));
        }
        try {
            accountService.updatePassword(id, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = 0;
        }
        resutltMap.put("isSuccess", isSuccess);
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/logout")
    @ResponseBody
    public Object logout(HttpSession session) {
        Map resutltMap = new HashMap();
        int isSuccess = 1;
        try {
            try {
                session.removeAttribute("loginUser");
            } catch (Exception e) {
                isSuccess=0;
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resutltMap.put("isSuccess", isSuccess);
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/sendMessage")
    @ResponseBody
    public Object sendMessage(String mobile) {
        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 1;
        Long mobileMsgId;
        {
            try {
                mobileMsgId = 999L;
                result.put("mobileMsgId", mobileMsgId);
            } catch (Exception e) {
                e.printStackTrace();
                isSuccess = 0;
            }
        }

        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        return JSON.toJSON(resutltMap);
    }


    @RequestMapping("/sendEmail")
    @ResponseBody
    public Object sendEmail(HttpSession session, String email, String veriCode) {
        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 1;
        int code = -1;

        veriCode = veriCode.toLowerCase();
        String verCode = "" + session.getAttribute("verCode");
        if (!veriCode.equals(verCode)) {
            code = 1;//验证码不正确
        } else {
            try {
                CmAccount account = accountService.getByAccount(email);
                String activityCode = account.getActivityCode();
                if (StringUtil.isNotEmpty(activityCode)) {
                    activityCode = "" + (int) ((Math.random() * 9 + 1) * 100000);
                    account.setActivityCode(activityCode);
                }

                StringBuffer emailContent = new StringBuffer();
                emailContent.append("点击下面链接修改账号，1小时有效，链接只能使用一次！/n");
                emailContent.append("<a href=\"");
                emailContent.append("http://");
                emailContent.append(ConstantsUtil.SERVER_IP);
                emailContent.append(":");
                emailContent.append(ConstantsUtil.SERVER_PORT);
                emailContent.append("/checkEmail?email=" + email + "&activityCode=" + activityCode);
                emailContent.append("\">");
                emailContent.append("http://");
                emailContent.append(ConstantsUtil.SERVER_IP);
                emailContent.append(":");
                emailContent.append(ConstantsUtil.SERVER_PORT);
                emailContent.append("/checkEmail?email=" + email + "&activityCode=" + activityCode);
                emailContent.append("</a>");

                SendEmailUtil.send(email, emailContent.toString());

                account.setSendEmailDate(new Date());
                accountService.save(account);
            } catch (Exception e) {
                isSuccess = 0;
                e.printStackTrace();
            }
        }

        result.put("code", code);
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/checkEmail")
    public String checkEmail(HttpSession session, String email, String activityCode, Map map) {
        CmAccount account = accountService.getByAccount(email);
        account.getSendEmailDate();
        DateUtil.addHour(new Date(), 1);
        if (DateUtil.addHour(new Date(), 1).before(account.getSendEmailDate())) {
            return "";
        } else if (!account.getActivityCode().equals(activityCode)) {
            return "";
        } else {
            map.put("email", email);
            String activityCodeNew = "" + (int) ((Math.random() * 9 + 1) * 100000);
            account.setActivityCode(activityCodeNew);
            accountService.save(account);
            return "redirect:/toChangePassword";
        }


    }

    /**
     * 短信校验用户名,手机号是否合法
     *
     * @param session
     * @param email
     * @param mobile
     * @param msgCode
     * @return
     */
    @RequestMapping(path = "/checkByMobile", method = RequestMethod.POST)
    @ResponseBody
    public Object checkByMobile(HttpSession session, String email, String mobile, String msgCode, Long mobileMsgId) {

        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 1;
        int code = -1;////code # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在
        {
            try {
                accountService.checkAccountIsExists(email);
                if (!accountService.checkAccountIsExists(email)) {
                    code = 1;//用户名不存在
                } else if (accountService.checkMobileIsExists(mobile)) {
                    code = 2;//手机号不存在
                } else {
                    if ("111111".equals(msgCode)) {
                        code = -1;
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


    @RequestMapping("/test")
    public void test() {
        String email = "915854720@qq.com";
        String activityCode = "986574";
        StringBuffer emailContent = new StringBuffer();
        emailContent.append("点击下面链接修改账号，1小时有效，链接只能使用一次!");
        emailContent.append("<a href=\"");
        emailContent.append("http://");
        emailContent.append(ConstantsUtil.SERVER_IP);
        emailContent.append(":");
        emailContent.append(ConstantsUtil.SERVER_PORT);
        emailContent.append("/checkEmail?email=" + email + "&activityCode=" + activityCode);
        emailContent.append("\">");
        emailContent.append("http://");
        emailContent.append(ConstantsUtil.SERVER_IP);
        emailContent.append(":");
        emailContent.append(ConstantsUtil.SERVER_PORT);
        emailContent.append("/checkEmail?email=" + email + "&activityCode=" + activityCode);
        emailContent.append("</a>");

        SendEmailUtil.send(email, emailContent.toString());

    }


    @RequestMapping("/auditStandard")
    public String auditStandard() {
        return "help-documentation/audit-standard";
    }

    @RequestMapping("/developmentDocumen")
    public String developmentDocumen() {
        return "help-documentation/development-documen";
    }


}
