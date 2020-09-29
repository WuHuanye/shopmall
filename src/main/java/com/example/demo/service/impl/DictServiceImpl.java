package com.example.demo.service.impl;

import com.example.demo.mapper.DictMapper1;
import com.example.demo.pojo.Dict;
import com.example.demo.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    DictMapper1 dictMapper;
    @Override
    public List<Dict> listByDictType(String type) {
        return dictMapper.listByDictType(type);
    }

    @Override
    public List<Dict> listParent() {
        return dictMapper.listParent();
    }

    @Override
    public List<Dict> chilerentListById(int id) {
        return dictMapper.chilerentListById(id);
    }

    @Override
    public Dict getChilDict(int id) {
        return dictMapper.getChilDict(id);
    }

    @Override
    public Dict getParDict(int id) {
        return dictMapper.getParDict(id);
    }
}
