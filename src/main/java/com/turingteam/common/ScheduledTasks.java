package com.turingteam.common;

import com.turingteam.domain.LabStatus;
import com.turingteam.service.ChairService;
import com.turingteam.service.LabStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class ScheduledTasks {
    @Autowired
    private ChairService chairService;

    @Autowired
    private LabStatusService labStatusService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 每天23:30执行
     */
    @Scheduled(cron = "0 30 23 * * ?")
    public void closeChairTask() {
        log.info("定时任务执行时间：" + dateFormat.format(new Date()));
        chairService.closeAllChair();
        labStatusService.updateById(new LabStatus(1, false));
        log.info("定时任务执行结束：" + dateFormat.format(new Date()));
    }
}
