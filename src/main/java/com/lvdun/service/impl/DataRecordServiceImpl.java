package com.lvdun.service.impl;

import com.lvdun.dao.DataRecordRepository;
import com.lvdun.entity.DataRecord;
import com.lvdun.queryModel.DataRecordQuery;
import com.lvdun.service.DataRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
}
