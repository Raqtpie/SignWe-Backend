package com.turingteam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turingteam.dao.LabStatusDao;
import com.turingteam.domain.LabStatus;
import com.turingteam.service.LabStatusService;
import org.springframework.stereotype.Service;

@Service
public class LabStatusServiceImpl extends ServiceImpl<LabStatusDao, LabStatus> implements LabStatusService {
}
