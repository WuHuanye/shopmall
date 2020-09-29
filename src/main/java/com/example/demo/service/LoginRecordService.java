package com.example.demo.service;

import com.example.demo.pojo.LoginRecord;

public interface LoginRecordService {
    void addLoginRecord(LoginRecord loginRecord);
    void cleanLoginRecordBeforeSevenDays();
}
