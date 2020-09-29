package com.example.demo.service;

import com.example.demo.pojo.Info;

public interface InfoService {
    String save(Info info);
    boolean update(Info info);
    String deleteById(int id);
}
