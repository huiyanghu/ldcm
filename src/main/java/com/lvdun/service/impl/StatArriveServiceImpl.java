package com.lvdun.service.impl;

import com.alibaba.fastjson.JSON;
import com.lvdun.dao.StatArriveRepository;
import com.lvdun.service.StatArriveService;
import com.lvdun.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service
public class StatArriveServiceImpl implements StatArriveService {
    @Autowired
    StatArriveRepository statArriveDao;



    @Override
    public Map getTotalResult(Integer flag, Long platformId) {
        Map map = statArriveDao.getTotalResult(flag, platformId);
        return map;
    }

    @Override
    public Map getChapterGeneral(Integer flag, Long platformId) {
        List<Map> chapterGeneral = statArriveDao.getChapterGeneral(flag, platformId);
        //String str_total = "[";
        //String str_pass = "[";

        List totalList = new ArrayList();
        List passList = new ArrayList();

        for (Map map : chapterGeneral) {
            List totalListTemp = new ArrayList();
            List passListTemp = new ArrayList();


            Date date = DateUtil.getDateByStr("" + map.get("time_stamp"));
            totalListTemp.add(date.getTime());
            passListTemp.add(date.getTime());

            Integer count_total = Integer.parseInt("" + map.get("count_total"));
            totalListTemp.add(count_total);
            Integer count_pass = Integer.parseInt("" + map.get("count_pass"));
            passListTemp.add(count_pass);

            totalList.add(totalListTemp);
            passList.add(passListTemp);
        }
        Map map = new HashMap();
        map.put("total", totalList);
        map.put("pass", passList);

        return map;
    }

    @Override
    public List<String> getDistinctApp(Long platformId) {
        List<Map> appList = statArriveDao.getDistinctApp(platformId);
        List<String> list = new ArrayList();
        for (Map map : appList) {
            list.add("" + map.get("app_name"));
        }
        System.out.println(JSON.toJSONString(list));
        return list;
    }

    @Override
    public List<String> getDistinctType(Long platformId) {
        List<Map> typeList = statArriveDao.getDistinctType(platformId);
        List<String> list = new ArrayList();
        for (Map map : typeList) {
            Integer type = Integer.parseInt("" + map.get("data_type"));
            if (type == 1) {
                list.add("文本");
            } else if (type == 2) {
                list.add("图片");
            }
        }
        return list;
    }

    @Override
    public List getChapterByType(Integer flag, Long platformId, String field) {
        List<Map> typeList = statArriveDao.getDistinctType(platformId);
        List bigList = new ArrayList();
        for (Map type : typeList) {
            List<Map> list = statArriveDao.getChapterByType(flag, platformId, Integer.parseInt("" + type.get("data_type")));
            List smallList = new ArrayList();
            for (Map map : list) {
                List littleList = new ArrayList();
                Date date = DateUtil.getDateByStr("" + map.get("time_stamp"));
                littleList.add(date.getTime());
                Integer count_total = Integer.parseInt("" + map.get(field));
                littleList.add(count_total);
                smallList.add(littleList);
            }
            bigList.add(smallList);
        }

        return bigList;
    }

    @Override
    public List getChapterByApp(Integer flag, Long platformId, String field) {
        List<Map> appList = statArriveDao.getDistinctApp(platformId);
        List bigList = new ArrayList();
        for (Map app : appList) {
            List<Map> list = statArriveDao.getChapterByApp(flag, platformId, Long.parseLong("" + app.get("id")));
            List smallList = new ArrayList();
            for (Map map : list) {
                List littleList = new ArrayList();
                Date date = DateUtil.getDateByStr("" + map.get("time_stamp"));
                littleList.add(date.getTime());
                Integer count_total = Integer.parseInt("" + map.get(field));
                littleList.add(count_total);
                smallList.add(littleList);
            }
            bigList.add(smallList);
        }
        return bigList;
    }

    @Override
    @Transactional
    public void executeProcedure() {
        statArriveDao.executeProcedure();
    }


}
