package com.hkb.hdms.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每天8点检查所有用户的待处理任务，发送邮件提醒
 *
 * @author huangkebing
 * 2021/04/15
 */
@Component
public class CronUtil {
    @Scheduled(cron="0/5 * * * * ?")
    public void CronJobMethod(){
        System.out.println("123");
    }
}
