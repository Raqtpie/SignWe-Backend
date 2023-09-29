package com.turingteam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turingteam.common.CustomException;
import com.turingteam.dao.ChairDao;
import com.turingteam.dao.RecordDao;
import com.turingteam.dao.UserRecordDao;
import com.turingteam.domain.Chair;
import com.turingteam.domain.Record;
import com.turingteam.domain.UserRecord;
import com.turingteam.service.ChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ChairServiceImpl extends ServiceImpl<ChairDao, Chair> implements ChairService {
    @Autowired
    private ChairDao chairDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private UserRecordDao userRecordDao;

    @Override
    @Transactional
    public void life(Integer chairId, Integer userId) {
        Chair chair = getById(chairId);
        if (userId.equals(chair.getUserId())) {
            throw new CustomException("当前用户不能签退该座位");
        }
        long now = System.currentTimeMillis();
        long time = chair.getTime();
        // 计算时间差
        long minutesDifference = (now - time) / (1000 * 60);
        chair.setStatus(false);
        updateById(chair);
        if (minutesDifference < 1) {
            return;
        }
        // 更新记录
        Record record = new Record(null, userId, (int) minutesDifference, LocalDate.now());
        recordDao.insert(record);
        // 更新用户记录
        UserRecord userRecord = userRecordDao.selectById(userId);
        if (userRecord == null) {
            userRecordDao.insert(new UserRecord(userId, (int) minutesDifference));
        } else {
            Integer totalTime = userRecord.getTotalTime();
            userRecord.setTotalTime(totalTime + (int) minutesDifference);
            userRecordDao.updateById(userRecord);
        }
    }

    /**
     * 关闭所有座位
     */
    @Override
    public void closeAllChair() {
        List<Chair> chairList = chairDao.selectList(null);
        for (Chair chair : chairList) {
            if (chair.getStatus()) {
                chair.setStatus(false);
                chairDao.updateById(chair);
            }
        }
    }
}
