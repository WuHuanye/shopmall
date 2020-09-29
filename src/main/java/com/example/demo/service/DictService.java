package com.example.demo.service;

import com.example.demo.pojo.Dict;

import java.util.List;

public interface DictService {
    List<Dict> listByDictType(String type);
    List<Dict> listParent();
    List<Dict> chilerentListById(int id);

    /*8.25*/
    Dict getChilDict(int id);
    Dict getParDict(int id);


}
