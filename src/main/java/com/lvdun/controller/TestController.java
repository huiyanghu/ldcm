package com.lvdun.controller;

import com.lvdun.dao.DataReSourceRepository;
import com.lvdun.dao.DataRecordRepository;
import com.lvdun.entity.DataRecord;
import com.lvdun.entity.DataResource;
import com.lvdun.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

/**
 * Created by Administrator on 2017/6/25.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    DataRecordRepository dataRecordRepository;
    @Autowired
    DataReSourceRepository dataReSourceRepository;


    @RequestMapping("/insertData")
    @ResponseBody
    public Object insertData() {

        String c="" + getRandom(6);
        /**
         * 插入data_record
         */
        for (int i = 0; i < 20000; i++) {
            DataRecord dataRecord = new DataRecord();
            dataRecord.setReasonCode(getRandom(9));
            dataRecord.setStatus(getRandom(2));//012
            dataRecord.setCodeAppId(Long.parseLong("" + getRandom(6)));
            dataRecord.setCreateDate(DateUtil.randomDate("2017-6-01 00:00:01", "2017-6-26 23:59:59"));
            dataRecord.setDataType(getRandom(1) + 1);
            dataRecord.setHasCount(0);
            dataRecord.setPublishDate(DateUtil.randomDate("2017-6-01 00:00:01", "2017-6-26 23:59:59"));
            dataRecord.setDigest(c+"测试data_record:"+i);
            dataRecord.setUserIp("用户ip:"+i);
            if (getRandom(1)==0){
                dataRecord.setOperatorId(1L);
            }else{
                dataRecord.setOperatorId(-1L);
            }

            dataRecordRepository.save(dataRecord);

            DataResource dataResource=new DataResource();
            dataResource.setDataRecordId(dataRecord.getId());
            dataResource.setContent("测试data_resource "+i);
            dataReSourceRepository.save(dataResource);
            System.out.println(i);
        }
        System.out.println("==========success===========");

        return 1;
    }

    public static Integer getRandom(int max) {
        Random random = new Random();// 定义随机类
        int result = random.nextInt(max + 1);// 返回[0,10)集合中的整数，注意不包括10
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {

            System.out.println(DateUtil.formatDate(DateUtil.randomDate("2017-6-1 00:10:01", "2017-6-25 23:59:59")));
        }

    }
}
