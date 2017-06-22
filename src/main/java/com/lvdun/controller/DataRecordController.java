package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.entity.CodeApp;
import com.lvdun.queryModel.DataRecordQuery;
import com.lvdun.service.CodeAppService;
import com.lvdun.service.DataRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
@Controller
@RequestMapping("/dataRecord")
public class DataRecordController {
    @Autowired
    DataRecordService dataRecordService;
    @Autowired
    CodeAppService codeAppService;

    @RequestMapping("/list")
    @ResponseBody
    public Object getPage(DataRecordQuery dataRecordQuery, HttpSession session, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page, @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        Long platformId = 1L;

        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 0;
        Map dataRecordPage = new HashMap();
        //List<CodeApp> codeAppList = new ArrayList();
        //List<Map> statusList = ConstantsUtil.getConstants("DataRecord_status");
        //List<Map> reasonCodeList = ConstantsUtil.getConstants("DataRecord_reasonCode");
        //List<Map> reviewTypeList = ConstantsUtil.getConstants("DataRecord_reviewType");
        //List<Map> condationList = ConstantsUtil.getConstants("DataRecord_condation");

        try {
            dataRecordPage = dataRecordService.getDataRecordPage(page, pageSize, platformId, dataRecordQuery);
            //codeAppList = codeAppService.getCodeAppByPlatformId(platformId);

            result.put("dataRecordPage", dataRecordPage);
            //result.put("codeAppList", codeAppList);
            //result.put("statusList", statusList);
            //result.put("reasonCodeList", reasonCodeList);
            //result.put("reviewTypeList", reviewTypeList);
            //result.put("condationList", condationList);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = 0;
        }

        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/getAppList")
    @ResponseBody
    public Object getAppList(HttpSession session) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        Long platformId = 1L;

        Map resutltMap = new HashMap();
        Map result = new HashMap();
        int isSuccess = 0;

        List<CodeApp> codeAppList = new ArrayList();
        try {
            codeAppList = codeAppService.getCodeAppByPlatformId(platformId);
            result.put("codeAppList", codeAppList);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = 0;
        }

        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", result);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }


    @RequestMapping(path = "/setStatus", method = RequestMethod.POST)
    @ResponseBody
    public Object setStatus(HttpSession session, Long id, Integer status) {
        Map resutltMap = new HashMap();
        int isSuccess = 0;
        try {
            dataRecordService.setStatus(id, status);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = 0;
        }
        resutltMap.put("isSuccess", isSuccess);
        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping(path = "/setReasonCode", method = RequestMethod.POST)
    @ResponseBody
    public Object setReasonCode(HttpSession session, Long id, Integer reasonCode) {
        Map resutltMap = new HashMap();
        int isSuccess = 0;
        try {
            dataRecordService.setReasonCode(id, reasonCode);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = 0;
        }
        resutltMap.put("isSuccess", isSuccess);
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/queryText")
    public String queryText(){
        return "data-inquiry/query-text";
    }

    @RequestMapping("/queryPicture")
    public String queryPicture(){
        return "data-inquiry/query-picture";
    }

}
