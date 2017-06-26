package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.service.CustomerService;
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
 * Created by Administrator on 2017/6/19.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    /**
     * 绿盾超级管理员有此功能
     *
     * @param session
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object getCustomerList(HttpSession session, @RequestParam(required = false, name = "page", defaultValue = "1") Integer page, @RequestParam(required = false, name = "pageSize", defaultValue = "10") Integer pageSize) {
        Map resutltMap = new HashMap();
        int isSuccess = 0;
        Map result = new HashMap();
        try {
            result = customerService.getCustomerPage(page, pageSize);
            isSuccess = 1;
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }

        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/reviewCustomer", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object reviewCustomer(HttpSession session, Long customerId) {

        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long customerId = Long.parseLong("" + loginUser.get("customerId"));

        int isSuccess = 0;
        try {
            customerService.reviewCustomer(customerId);
            isSuccess = 1;
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);

        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    /**
     * 客户信息
     *
     * @param session
     * @return
     */
    @RequestMapping("/getCustomerInfo")
    @ResponseBody
    public Object getCustomerInfo(HttpSession session) {

        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long customerId = Long.parseLong("" + loginUser.get("customerId"));
        Long customerId = 1L;
        Map customerInfo = new HashMap();
        int isSuccess = 0;
        try {
            customerInfo = customerService.getCustomerInfo(customerId);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", customerInfo);

        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/updateBasicInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object updateBasicInfo(HttpSession session, String customerName, String contactsName, String contactsMobile, String approvalTime, String province, String city, String region) {

        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long customerId = Long.parseLong("" + loginUser.get("customerId"));
        Long customerId = 1L;
        int isSuccess = 0;
        try {
            customerService.updateCustomerBasicInfo(customerId, customerName, contactsName, contactsMobile, approvalTime, province, city, region);
            isSuccess = 1;
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);

        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/customerInfo")
    public String customerInfo(){
        return "customer-info/customer-info";
    }

    @RequestMapping("/privilegeManagement")
    public String privilegeManagement(){
        return "privilege-management/privilege-management";
    }

}
