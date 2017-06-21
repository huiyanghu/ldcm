package com.lvdun.dao;

import com.lvdun.entity.DataRecord;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface DataRecordRepository extends CrudRepository<DataRecord, Long>, DataRecordDao {

}
