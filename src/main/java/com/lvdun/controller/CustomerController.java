package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/19.
 */
@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;

    /**
     * 客户信息
     * @param session
     * @return
     */
    @RequestMapping("/getCustomerInfo")
    @ResponseBody
    public Object getCustomerInfo(HttpSession session) {

        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long customerId = Long.parseLong("" + loginUser.get("customerId"));
        Long customerId=1L;
        Map customerInfo=new HashMap();
        int isSuccess = 0;
        try {
            customerInfo= customerService.getCustomerInfo(customerId);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", customerInfo);

        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/updateBasicInfo")
    @ResponseBody
    public Object updateBasicInfo(HttpSession session) {

        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long customerId = Long.parseLong("" + loginUser.get("customerId"));
        Long customerId=1L;
        Map customerInfo=new HashMap();
        int isSuccess = 0;
        try {
            customerInfo= customerService.getCustomerInfo(customerId);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", customerInfo);

        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }


}
