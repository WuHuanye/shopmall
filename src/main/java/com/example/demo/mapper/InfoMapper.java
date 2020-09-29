package com.example.demo.mapper;

import com.example.demo.pojo.Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface InfoMapper {
    int save(Info info);
    int update(Info info);
    int deleteById(int rid);
    List<Info> queryInfoListByInfo(@Param("info") Info info,@Param("start")int start,@Param("pageSize")int pageSize);
    int getInfoTotalCount(@Param("info")Info info);
}
