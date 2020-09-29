package com.example.demo.service;

import com.example.demo.pojo.CarItem;

import java.util.List;

public interface CarItemService {
    String addCarItem(CarItem carItem);
    List<CarItem> queryCarItemsByUserId(int userId);
    String delCarItemById(int carItemId);
}
