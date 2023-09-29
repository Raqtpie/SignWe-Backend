package com.turingteam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turingteam.dao.RecordDao;
import com.turingteam.dao.UserDao;
import com.turingteam.dao.UserRecordDao;
import com.turingteam.domain.Record;
import com.turingteam.domain.User;
import com.turingteam.domain.UserRecord;
import com.turingteam.domain.dto.RecordDto;
import com.turingteam.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRecordServiceImpl extends ServiceImpl<UserRecordDao, UserRecord> implements UserRecordService {
    @Autowired
    private UserRecordDao userRecordDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<RecordDto> getList() {
        List<UserRecord> userRecords = userRecordDao.selectList(null);
        userRecords.sort((o1, o2) -> o2.getTotalTime() - o1.getTotalTime());
        List<RecordDto> recordDtoList = new ArrayList<>();
        for (UserRecord userRecord : userRecords) {
            Integer userId = userRecord.getUserId();
            User user = userDao.selectById(userId);
            RecordDto recordDto = new RecordDto();
            recordDto.setUserId(userId);
            recordDto.setUsername(user.getName());
            recordDto.setClassName(user.getClassName());
            recordDto.setTotalTime(userRecord.getTotalTime());
            recordDtoList.add(recordDto);
        }
        return recordDtoList;
    }

    @Override
    public UserRecord getTotalTimeByUserId(Integer userId) {
        LambdaQueryWrapper<UserRecord> userRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRecordLambdaQueryWrapper.eq(UserRecord::getUserId, userId);
        UserRecord userRecord = userRecordDao.selectOne(userRecordLambdaQueryWrapper);
        return userRecord;
    }

    @Override
    public List<RecordDto> getRecordYesterday() {
        LambdaQueryWrapper<Record> recordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LocalDate previousDate = LocalDate.now().minusDays(1);
        recordLambdaQueryWrapper.eq(Record::getDate, previousDate);
        List<Record> recordList = recordDao.selectList(recordLambdaQueryWrapper);
        Map<Integer, Integer> integerIntegerMap = new HashMap<>();
        for (Record record : recordList) {
            Integer userId = record.getUserId();
            Integer time = record.getTime();
            if (integerIntegerMap.containsKey(userId)) {
                integerIntegerMap.put(userId, integerIntegerMap.get(userId) + time);
            } else {
                integerIntegerMap.put(userId, time);
            }
        }
        List<RecordDto> recordDtoList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : integerIntegerMap.entrySet()) {
            Integer userId = entry.getKey();
            Integer time = entry.getValue();
            User user = userDao.selectById(userId);
            RecordDto recordDto = new RecordDto();
            recordDto.setUserId(userId);
            recordDto.setUsername(user.getName());
            recordDto.setClassName(user.getClassName());
            recordDto.setTotalTime(time);
            recordDtoList.add(recordDto);
        }
        recordDtoList.sort((o1, o2) -> o2.getTotalTime() - o1.getTotalTime());
        return recordDtoList;
    }
}
