package com.lvdun.schedule;

import com.lvdun.service.StatArriveService;
import com.lvdun.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class ScheduledCountDataRecord {
    @Autowired
    StatArriveService statArriveService;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledCountDataRecord.class);
    public final static long HALF_MINUTE = 30 * 1000;
    public final static long ONE_MINUTE = 60 * 1000;
    public final static long ONE_HOUR = 60 * 60 * 1000;

    //@Scheduled(cron = "0 0 * * * ?")//整点执行
    //@Scheduled(cron="0 * * * * ?")//整分执行
    //@Scheduled(cron="0/5 * *  * * ? ")//每5秒执行一次
    @Scheduled(cron="0 0/10 *  * * ? ")//每10分钟执行一次
    //@Scheduled(fixedRate=ONE_MINUTE)
    public void executeFileDownLoadTask() {
        System.out.println(" =============executeProcedure_start:" + DateUtil.getTimeStampStr(new Date()));
        statArriveService.executeProcedure();
        System.out.println(" =============executeProcedure_end  :" + DateUtil.getTimeStampStr(new Date()));
    }


}
