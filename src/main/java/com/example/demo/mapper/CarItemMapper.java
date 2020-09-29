package com.example.demo.mapper;

import com.example.demo.pojo.CarItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CarItemMapper {
    int addCarItem(CarItem carItem);
    List<CarItem> queryCarItemsByUserId(int userId);
    int delCarItemById(int carItemId);

}
