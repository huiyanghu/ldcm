package com.lvdun.controller;

import com.alibaba.fastjson.JSON;
import com.lvdun.service.StatArriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
@Controller
@RequestMapping("/statArrive")
public class StatArriveController {
    @Autowired
    StatArriveService statArriveService;

    /**
     * 接入量趋势分析
     *
     * @param flag
     * @param session
     * @return
     */
    @RequestMapping("/getTotalResult")
    @ResponseBody
    public Object getTotalResult(Integer flag, HttpSession session) {


        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        Long platformId = 1L;
        Map map = new HashMap();
        int isSuccess = 0;
        try {
            map = statArriveService.getTotalResult(flag, platformId);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutlt = new HashMap();
        resutlt.put("isSuccess", isSuccess);
        resutlt.put("result", map);

        System.out.println(JSON.toJSONString(resutlt));
        return JSON.toJSON(resutlt);
    }

    /**
     * 总图
     *
     * @param flag
     * @param session
     * @return
     */
    @RequestMapping("/getChapterGeneral")
    @ResponseBody
    public Object getChapterGeneral(Integer flag, HttpSession session) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        Long platformId = 1L;
        Map chapterGeneralMap = new HashMap();
        int isSuccess = 0;
        try {
            chapterGeneralMap = statArriveService.getChapterGeneral(flag, platformId);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutlt = new HashMap();
        resutlt.put("isSuccess", isSuccess);
        resutlt.put("result", chapterGeneralMap);

        System.out.println(JSON.toJSONString(resutlt));
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
    public String getChapterByType(Integer flag, HttpSession session) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        Long platformId = 1L;
        Map map = new HashMap();
        int isSuccess = 0;
        try {
            List totalList = statArriveService.getChapterByType(flag, platformId, "count_total");
            List passList = statArriveService.getChapterByType(flag, platformId, "count_pass");
            List<String> typeList = statArriveService.getDistinctType(platformId);
            map.put("total", totalList);
            map.put("pass", passList);
            map.put("type", typeList);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", map);

        System.out.println(JSON.toJSONString(resutltMap));
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
    public Object getChapterByApp(Integer flag, HttpSession session) {
        Map loginUser = (Map) session.getAttribute("loginUser");
        //Long platformId=Long.parseLong(""+loginUser.get("platformId"));
        Long platformId = 1L;
        Map map = new HashMap();
        int isSuccess = 0;
        try {
            List totalList = statArriveService.getChapterByApp(flag, platformId, "count_total");
            List passList = statArriveService.getChapterByApp(flag, platformId, "count_pass");
            List<String> appList = statArriveService.getDistinctApp(platformId);
            map.put("total", totalList);
            map.put("pass", passList);
            map.put("app", appList);
            isSuccess = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resutltMap = new HashMap();
        resutltMap.put("isSuccess", isSuccess);
        resutltMap.put("result", map);

        System.out.println(JSON.toJSONString(resutltMap));
        return JSON.toJSON(resutltMap);
    }

    @RequestMapping("/test")
    public void test() {
        statArriveService.getDistinctApp(1L);
    }

}
