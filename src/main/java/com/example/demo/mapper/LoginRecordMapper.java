package com.example.demo.mapper;

import com.example.demo.pojo.LoginRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LoginRecordMapper {
    int addLoginRecord(LoginRecord record);
    List<LoginRecord> queryLoginRecords(@Param("loginRecord") LoginRecord loginRecord,@Param("start") int start,@Param("pageSize") int pageSize);
    int getRecordTotalCount(@Param("loginRecord") LoginRecord loginRecord);
    void cleanLoginRecordBeforeSevenDays();
}
