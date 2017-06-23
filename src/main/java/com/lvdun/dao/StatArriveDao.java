package com.lvdun.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/17.
 */

public interface StatArriveDao {
    public Map getTotalResult(Integer flag, Long platformId);

    public List<Map> getChapterGeneral(Integer flag, Long platformId);

    public List<Map> getDistinctType(Long platformId);

    public List<Map> getDistinctApp(Long platformId);

    public List<Map> getChapterByType(Integer flag, Long platformId, Integer type);

    public List<Map> getChapterByApp(Integer flag, Long platformId, Long appId);

    //public void insertIntoStatArrive();
}
