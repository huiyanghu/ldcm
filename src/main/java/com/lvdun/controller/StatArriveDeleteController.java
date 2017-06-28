package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.service.StatArriveDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
@Controller
@RequestMapping("/statArriveDelete")
public class StatArriveDeleteController {
    @Autowired
    StatArriveDeleteService statArriveDeleteService;


    /**
     * 总图
     *
     * @param flag
     * @param session
     * @return
     */
    @RequestMapping("/getChapterGeneral")
    @ResponseBody
    public Object getChapterGeneral(@RequestParam(defaultValue = "0",required = false,name="flag") Integer flag, HttpSession session) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        //Long platformId = 1L;
        Map chapterGeneralMap = new HashMap();
        int isSuccess = 0;
        try {
            chapterGeneralMap = statArriveDeleteService.getChapterGeneral(flag, platformId);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutlt = new HashMap();
        resutlt.put("isSuccess", isSuccess);
        resutlt.put("result", chapterGeneralMap);

        //System.out.println(JSON.toJSONString(resutlt));
        return JSON.toJSON(resutlt);
    }

    /**
     * 按 类型 统计
     *
     * @param flag
     * @param session
     * @return
     */
    @RequestMapping("/getChapterByType")
    @ResponseBody
    public String getChapterByType(@RequestParam(defaultValue = "0",required = false,name="flag") Integer flag, HttpSession session) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        //Long platformId = 1L;
        Map map = new HashMap();
        int isSuccess = 1;
        try {
            List sqList = statArriveDeleteService.getChapterByType(flag, platformId, "count_sq");
            List zzList = statArriveDeleteService.getChapterByType(flag, platformId, "count_zz");
            List wfList = statArriveDeleteService.getChapterByType(flag, platformId, "count_wf");
            List wgList = statArriveDeleteService.getChapterByType(flag, platformId, "count_wg");
            List<String> typeList = statArriveDeleteService.getDistinctType(platformId);
            map.put("sq", sqList);
            map.put("zz", zzList);
            map.put("wf", wfList);
            map.put("wg", wgList);
            map.put("type", typeList);
        } catch (Exception e) {
            isSuccess = 0;
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", map);

        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSONString(resutltMap);
    }

    /**
     * 按 app 统计
     *
     * @param flag
     * @param session
     * @return
     */
    @RequestMapping("/getChapterByApp")
    @ResponseBody
    public Object getChapterByApp(@RequestParam(defaultValue = "0",required = false,name="flag") Integer flag, HttpSession session) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        //Long platformId = 1L;
        Map map = new HashMap();
        int isSuccess = 0;
        try {
            List sqList = statArriveDeleteService.getChapterByApp(flag, platformId, "count_sq");
            List zzList = statArriveDeleteService.getChapterByApp(flag, platformId, "count_zz");
            List wfList = statArriveDeleteService.getChapterByApp(flag, platformId, "count_wf");
            List wgList = statArriveDeleteService.getChapterByApp(flag, platformId, "count_wg");
            List<String> appList = statArriveDeleteService.getDistinctApp(platformId);
            map.put("sq", sqList);
            map.put("zz", zzList);
            map.put("wf", wfList);
            map.put("wg", wgList);
            map.put("app", appList);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", map);

        //System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }



}
