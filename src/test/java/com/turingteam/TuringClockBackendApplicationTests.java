package com.turingteam;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.turingteam.dao.RecordDao;
import com.turingteam.dao.UserDao;
import com.turingteam.domain.Record;
import com.turingteam.domain.User;
import com.turingteam.domain.dto.RecordDto;
import com.turingteam.service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class TuringClockBackendApplicationTests {
    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
    }

}
