package com.example.demo.service.impl;

import com.example.demo.mapper.IndexConfigMapper;
import com.example.demo.pojo.IndexConfig;
import com.example.demo.service.IndexConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexConfigServiceImpl implements IndexConfigService {
    @Autowired
    IndexConfigMapper indexConfigMapper;
    @Override
    public String addIndexConfig(IndexConfig indexConfig) {
        String result = "error";
        if(indexConfigMapper.addIndexConfig(indexConfig)>0){
            result = "success";
        }
        return result;
    }
    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        String result = "error";
        if(indexConfigMapper.updateIndexConfig(indexConfig)>0){
            result = "success";
        }
        return result;
    }

    @Override
    public IndexConfig queryIndexConfig(int configId) {
        return indexConfigMapper.queryIndexConfig(configId);
    }

    @Override
    public String delIndexConfig(int configId) {
        if(indexConfigMapper.delIndexConfig(configId) > 0){
            return "success";
        }
        return "error";


    }

    /**
     * 商品配置首页展示
     * @param configType
     * @param number
     * @return
     */
    @Override
    public List<IndexConfig> selectTypeShow(int configType, int number) {
        //首页配置的内容因为不经常改动，所以放在redis会更好，但暂时还没有这样做
        return indexConfigMapper.selectTypeShow(configType,number);
    }
}
