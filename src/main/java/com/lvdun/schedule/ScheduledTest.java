package com.lvdun.schedule;

import com.lvdun.service.StatArriveService;
import com.lvdun.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class ScheduledTest {
    @Autowired
    StatArriveService statArriveService;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTest.class);
    public final static long HALF_MINUTE = 30 * 1000;
    public final static long ONE_MINUTE = 60 * 1000;
    public final static long ONE_HOUR = 60 * 60 * 1000;

    //@Scheduled(cron="0 1 * * * ?")
    //@Scheduled(fixedRate=HALF_MINUTE)
    public void executeFileDownLoadTask() {
        statArriveService.insertStatArrive();
        System.out.println(DateUtil.getTimeStampStr(new Date())+" ========fixedRate执行");
    }

    //@Scheduled(cron="0 1 * * * ?")
    public void executeUploadTask() {

        // 间隔1分钟,执行工单上传任务
        Thread current = Thread.currentThread();
        System.out.println("定时任务2:" + current.getId());
        logger.info("ScheduledTest.executeUploadTask 定时任务2:" + current.getId() + ",name:" + current.getName());
    }

    //@Scheduled(cron="0 0/3 5-23 * * ?")
    public void executeUploadBackTask() {

        // 间隔3分钟,执行工单上传任务
        Thread current = Thread.currentThread();
        System.out.println("定时任务3:" + current.getId());
        logger.info("ScheduledTest.executeUploadBackTask 定时任务3:" + current.getId() + ",name:" + current.getName());
    }

}
