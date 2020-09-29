package com.example.demo.config;

import com.example.demo.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务 清除登录记录
 * @desc 每周一凌晨五点整清除上一周的的登录记录
 * @sql delete from tb_user where DATEDIFF(NOW(),create_time)>7 周日晚上清除七天之前的数据
 */
@Component
public class RecordConfig {
    @Autowired
    LoginRecordService loginRecordService;

    @Scheduled(cron = "* 0 5 * * 1")
    public void cleanLoginRecordBeforeSevenDays(){
        loginRecordService.cleanLoginRecordBeforeSevenDays();
    }
}
