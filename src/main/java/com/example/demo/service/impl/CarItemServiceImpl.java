package com.example.demo.service.impl;

import com.example.demo.mapper.CarItemMapper;
import com.example.demo.pojo.CarItem;
import com.example.demo.service.CarItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarItemServiceImpl implements CarItemService {
    @Autowired
    CarItemMapper carItemMapper;
    @Override
    public String addCarItem(CarItem carItem) {
        String result = "error";
        if(carItemMapper.addCarItem(carItem) > 0){
            result = "success";
        }
        return result;
    }

    @Override
    public List<CarItem> queryCarItemsByUserId(int userId) {
       return carItemMapper.queryCarItemsByUserId(userId);
    }

    @Override
    public String delCarItemById(int carItemId) {
        if(carItemMapper.delCarItemById(carItemId) > 0){
            return "success";
        }
        return "error";
    }
}
