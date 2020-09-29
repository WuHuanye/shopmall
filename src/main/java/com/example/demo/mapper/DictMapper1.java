package com.example.demo.mapper;

import com.example.demo.pojo.Dict;
import com.example.demo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DictMapper1 {
    List<Dict> listByDictType(String type);
    List<Dict> listParent();

    List<Dict> chilerentListById(int id);

    /*8.25*/
    Dict getChilDict(int id);
    Dict getParDict(int id);


}
