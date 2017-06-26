package com.lvdun.service.impl;

import com.lvdun.dao.StatArriveDeleteRepository;
import com.lvdun.service.StatArriveDeleteService;
import com.lvdun.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service
public class StatArriveDeleteServiceImpl implements StatArriveDeleteService {
    @Autowired
    StatArriveDeleteRepository statArriveDeleteDao;



    @Override
    public Map getTotalResult(Integer flag, Long platformId) {
        Map map = statArriveDeleteDao.getTotalResult(flag, platformId);
        return map;
    }

    @Override
    public Map getChapterGeneral(Integer flag, Long platformId) {
        List<Map> chapterGeneral = statArriveDeleteDao.getChapterGeneral(flag, platformId);

        List sqList = new ArrayList();
        List zzList = new ArrayList();
        List wfList = new ArrayList();
        List wgList = new ArrayList();

        for (Map map : chapterGeneral) {
            List sqListTemp = new ArrayList();
            List zzListTemp = new ArrayList();
            List wfListTemp = new ArrayList();
            List wgListTemp = new ArrayList();


            Date date = DateUtil.getDateByStr("" + map.get("time_stamp"));
            long time=date.getTime();
            sqListTemp.add(time);
            zzListTemp.add(time);
            wfListTemp.add(time);
            wgListTemp.add(time);

            Integer count_sq = Integer.parseInt("" + map.get("count_sq"));
            sqListTemp.add(count_sq);
            Integer count_zz = Integer.parseInt("" + map.get("count_zz"));
            zzListTemp.add(count_zz);
            Integer count_wf = Integer.parseInt("" + map.get("count_wf"));
            wfListTemp.add(count_wf);
            Integer count_wg = Integer.parseInt("" + map.get("count_wg"));
            wgListTemp.add(count_wg);

            sqList.add(sqListTemp);
            zzList.add(zzListTemp);
            wfList.add(wfListTemp);
            wgList.add(wgListTemp);
        }
        Map map = new HashMap();
        map.put("sq", sqList);
        map.put("zz", zzList);
        map.put("wf", wfList);
        map.put("wg", wgList);

        return map;
    }

    @Override
    public List<String> getDistinctApp(Long platformId) {
        List<Map> appList = statArriveDeleteDao.getDistinctApp(platformId);
        List<String> list = new ArrayList();
        for (Map map : appList) {
            list.add("" + map.get("app_name"));
        }
        return list;
    }

    @Override
    public List<String> getDistinctType(Long platformId) {
        List<Map> typeList = statArriveDeleteDao.getDistinctType(platformId);
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
        List<Map> typeList = statArriveDeleteDao.getDistinctType(platformId);
        List bigList = new ArrayList();
        for (Map type : typeList) {
            List<Map> list = statArriveDeleteDao.getChapterByType(flag, platformId, Integer.parseInt("" + type.get("data_type")));
            List smallList = new ArrayList();
            for (Map map : list) {
                List littleList = new ArrayList();
                Date date = DateUtil.getDateByStr("" + map.get("time_stamp"));
                littleList.add(date.getTime());
                Integer count = Integer.parseInt("" + map.get(field));
                littleList.add(count);
                smallList.add(littleList);
            }
            bigList.add(smallList);
        }

        return bigList;
    }

    @Override
    public List getChapterByApp(Integer flag, Long platformId, String field) {
        List<Map> appList = statArriveDeleteDao.getDistinctApp(platformId);
        List bigList = new ArrayList();
        for (Map app : appList) {
            List<Map> list = statArriveDeleteDao.getChapterByApp(flag, platformId, Long.parseLong("" + app.get("id")));
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




}
