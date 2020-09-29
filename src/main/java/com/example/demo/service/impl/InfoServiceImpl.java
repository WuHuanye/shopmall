package com.example.demo.service.impl;

import com.example.demo.mapper.InfoMapper;
import com.example.demo.pojo.Info;
import com.example.demo.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    InfoMapper infoMapper;
    @Override
    public String save(Info info) {
        if(infoMapper.save(info) > 0){
            return "success";
        }
        return "error";
    }

    @Override
    public boolean update(Info info) {
        if (infoMapper.update(info) > 0){
            return true;
        }
        return false;
    }

    @Override
    public String deleteById(int id) {
        if (infoMapper.deleteById(id) > 0){
            return "success";
        }
        return "error";
    }
}
