package com.example.demo.mapper;

import com.example.demo.pojo.IndexConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface IndexConfigMapper {
    List<IndexConfig> queryIndexConfigList(@Param("indexConfig") IndexConfig indexConfig,@Param("start") int start,@Param("pageSize") int pageSize);
    int getIndexConfigTotalCount(@Param("indexConfig")IndexConfig indexConfig);  //  获取数量
    int addIndexConfig(IndexConfig indexConfig);
    int updateIndexConfig(IndexConfig indexConfig);

    IndexConfig queryIndexConfig(int configId);
    int delIndexConfig(int configId);

    List<IndexConfig> selectTypeShow(@Param("configType") int configType,@Param("number") int number);

}
