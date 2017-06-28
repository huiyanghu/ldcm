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
        DataRecord dataRecord = new DataRecord();
        DataResource dataResource=new DataResource();
        /**
         * 插入data_record,data_resource
         */
        for (int i = 0; i < 10000; i++) {
            dataRecord = new DataRecord();
            dataRecord.setReasonCode(getRandom(9));
            dataRecord.setStatus(getRandom(2));//012
            dataRecord.setCodeAppId(Long.parseLong("" + getRandom(6)));
            dataRecord.setCreateDate(DateUtil.randomDate("2017-6-1 00:00:01", "2017-7-1 23:59:59"));
            dataRecord.setDataType(getRandom(1) + 1);
            dataRecord.setHasCount(0);
            dataRecord.setPublishDate(DateUtil.randomDate("2017-6-1 00:00:01", "2017-7-1 23:59:59"));
            dataRecord.setDigest(c+"测试data_record:"+i);
            dataRecord.setUserIp("用户ip:"+i);
            dataRecord.setUserPublishDataId("UserPublishDataId:"+i);
            if (getRandom(1)==0){
                dataRecord.setOperatorId(1L);
            }else{
                dataRecord.setOperatorId(-1L);
            }

            dataRecordRepository.save(dataRecord);

            dataResource=new DataResource();
            dataResource.setId(dataRecord.getId());
            dataResource.setContent("测试data_resource----"+i+"----The Apache Tomcat® software is an open source implementation of the Java Servlet, 凤凰网是中国领先的综合门户网站,提供含文图音视频的全方位综合新闻资讯、深度访谈、观点评论、财经产品、互动应用、分享社区等服务,同时与凤凰无线、凤凰宽频形成三JavaServer Pages, Java Expression Language and Java WebSocket technologies. The Java Servlet, JavaServer Pages, Java Expression Language and Java WebSocket specifications are developed under the Java Community Process.\n" +
                    "\n" +
                    "The Apache Tomcat software is developed in an open and participatory environment and released under the Apache License version 2. The Apache Tomcat project is intended to be a collaboration of the best-of-breed developers from around the world. We invite you to participate in this open development project. To learn more about getting involved, click here.\n" +
                    "\n" +
                    "Apache Tomcat software powers numerous large-scale, mission-critical web applications across a diverse range of industries and organizations. Some of these users and their stories are listed on the PoweredBy wiki page.\n" +
                    "\n" +
                    "Apache Tomcat, Tomcat, Apache, the Apache feather, and the Apache Tomcat project logo are trademarks of the Apache Software Foundation.");
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

    @RequestMapping("/test500")
    public String test500(){
        System.out.println(1/0);
        return "";
    }

    @RequestMapping("/list")
    public String list(){

        return "test/list";
    }

}
