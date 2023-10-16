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
            String userId = userRecord.getUserId();
            User user = userDao.selectById(userId);
            RecordDto recordDto = new RecordDto();
            recordDto.setUsername(user.getName());
            recordDto.setClassName(user.getClassName());
            recordDto.setTotalCount(userRecord.getTotalCount());
            recordDto.setTotalTime(userRecord.getTotalTime());
            recordDtoList.add(recordDto);
        }
        return recordDtoList;
    }

    @Override
    public UserRecord getTotalTimeByUserId(String userId) {
        LambdaQueryWrapper<UserRecord> userRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRecordLambdaQueryWrapper.eq(UserRecord::getUserId, userId);
        return userRecordDao.selectOne(userRecordLambdaQueryWrapper) == null ? new UserRecord(null, 0, 0) : userRecordDao.selectOne(userRecordLambdaQueryWrapper);
    }

    // FIXME: 此处返回结果为了与GetTotalTimeByUserId一致返回了UserRecord，实际两个接口应该返回RecordDto或新建一个类作为返回结果
    // FIXME: 由于前端已经完成代码编写，此处暂时不做修改
    @Override
    public UserRecord getTotalTimeByUserIdToday(String userId) {
        LambdaQueryWrapper<Record> recordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LocalDate now = LocalDate.now();
        recordLambdaQueryWrapper.eq(Record::getUserId, userId).eq(Record::getDate, now);
        List<Record> recordList = recordDao.selectList(recordLambdaQueryWrapper);
        int totalCount = 0;
        int totalTime = 0;
        for (Record record : recordList) {
            totalCount++;
            totalTime += record.getTime();
        }
        return new UserRecord(userId, totalCount, totalTime);
    }


    @Override
    public List<RecordDto> getRecordYesterday() {
        LambdaQueryWrapper<Record> recordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LocalDate previousDate = LocalDate.now().minusDays(1);
        recordLambdaQueryWrapper.eq(Record::getDate, previousDate);
        List<Record> recordList = recordDao.selectList(recordLambdaQueryWrapper);
        Map<String, Integer> integerIntegerMap = new HashMap<>();
        for (Record record : recordList) {
            String userId = record.getUserId();
            Integer time = record.getTime();
            if (integerIntegerMap.containsKey(userId)) {
                integerIntegerMap.put(userId, integerIntegerMap.get(userId) + time);
            } else {
                integerIntegerMap.put(userId, time);
            }
        }
        List<RecordDto> recordDtoList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : integerIntegerMap.entrySet()) {
            String userId = entry.getKey();
            Integer time = entry.getValue();
            User user = userDao.selectById(userId);
            RecordDto recordDto = new RecordDto();
            recordDto.setUsername(user.getName());
            recordDto.setClassName(user.getClassName());
            recordDto.setTotalTime(time);
            recordDtoList.add(recordDto);
        }
        recordDtoList.sort((o1, o2) -> o2.getTotalTime() - o1.getTotalTime());
        recordDtoList.forEach(recordDto -> recordDto.setUserId(null));
        return recordDtoList;
    }
}
