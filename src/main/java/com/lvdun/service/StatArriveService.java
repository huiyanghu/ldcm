package com.lvdun.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface StatArriveService {
    public void insertStatArrive();

    public Map getTotalResult(Integer flag, Long platformId);

    public Map getChapterGeneral(Integer flag, Long platformId);

    public List<String> getDistinctApp(Long platformId);

    public List<String> getDistinctType(Long platformId);

    public List getChapterByType(Integer flag, Long platformId, String field);

    public List getChapterByApp(Integer flag, Long platformId, String field);

}
