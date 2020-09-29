package com.example.demo.service;

import com.example.demo.pojo.IndexConfig;

import java.util.List;

public interface IndexConfigService {
    String addIndexConfig(IndexConfig indexConfig);
    String updateIndexConfig(IndexConfig indexConfig);
    IndexConfig queryIndexConfig(int configId);

    String delIndexConfig(int configId);
    /*首页商品配置展示*/
    List<IndexConfig> selectTypeShow(int configType,int number);
}
