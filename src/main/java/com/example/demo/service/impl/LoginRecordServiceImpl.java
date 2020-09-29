package com.example.demo.service.impl;

import com.example.demo.mapper.LoginRecordMapper;
import com.example.demo.pojo.LoginRecord;
import com.example.demo.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {
    @Autowired
    LoginRecordMapper recordMapper;
    @Override
    public void addLoginRecord(LoginRecord loginRecord) {
        recordMapper.addLoginRecord(loginRecord);
    }

    @Override
    public void cleanLoginRecordBeforeSevenDays() {
        recordMapper.cleanLoginRecordBeforeSevenDays();
    }
}
