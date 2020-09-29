package com.example.demo.utils;

import com.example.demo.pojo.Dict;
import com.example.demo.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictUtils {
    @Autowired
    static DictService dictService;
    public static List<Dict> listByDictType(String type){
        return dictService.listByDictType(type);
    }

    public static List<Dict> listParent(){
        return dictService.listParent();
    }
}
