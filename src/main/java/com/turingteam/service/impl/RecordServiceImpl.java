package com.turingteam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turingteam.dao.RecordDao;
import com.turingteam.domain.Record;
import com.turingteam.service.RecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordDao, Record> implements RecordService {
    @Override
    public List<Record> getRecordByUserId(String userId) {
        LambdaQueryWrapper<Record> recordLambdaQueryChainWrapper = new LambdaQueryWrapper<>();
        recordLambdaQueryChainWrapper.eq(Record::getUserId, userId);
        List<Record> list = list(recordLambdaQueryChainWrapper);
        list.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        return list;
    }
}
