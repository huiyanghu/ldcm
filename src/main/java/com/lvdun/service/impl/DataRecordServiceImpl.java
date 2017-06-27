package com.lvdun.service.impl;

import com.alibaba.fastjson.JSON;
import com.lvdun.dao.DataRecordRepository;
import com.lvdun.entity.DataRecord;
import com.lvdun.queryModel.DataRecordQuery;
import com.lvdun.service.DataRecordService;
import com.lvdun.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
@Service
public class DataRecordServiceImpl implements DataRecordService {
    @Autowired
    DataRecordRepository dataRecordDao;

    @Override
    public Map getDataRecordPage(Integer page, Integer pageSize, Long platformId, DataRecordQuery dataRecordQuery) {
        return dataRecordDao.getDataRecordPage(page, pageSize, platformId, dataRecordQuery);
    }

    @Override
    @Transactional
    public void setStatus(Long id, Integer status) {
        DataRecord dataRecord = dataRecordDao.findOne(id);
        dataRecord.setStatus(status);
        dataRecordDao.save(dataRecord);
    }

    @Override
    @Transactional
    public void setReasonCode(Long id, Integer reasonCode) {
        DataRecord dataRecord = dataRecordDao.findOne(id);
        dataRecord.setReasonCode(reasonCode);
        dataRecordDao.save(dataRecord);
    }

    @Override
    @Transactional
    public void setReasonCodeBatch(String reasonCodeJson) {
        if (StringUtil.isNotEmpty(reasonCodeJson)) {
            List<Map> list = (List<Map>) JSON.parse(reasonCodeJson);
            for (Map map : list) {
                DataRecord dataRecord = dataRecordDao.findOne(Long.parseLong("" + map.get("id")));
                dataRecord.setStatus(Integer.parseInt("" + map.get("status")));
                dataRecord.setReasonCode(Integer.parseInt("" + map.get("reason")));
                dataRecordDao.save(dataRecord);
            }
        }
    }

    @Override
    public void save(DataRecord dataRecord) {
        dataRecordDao.save(dataRecord);
    }
}
