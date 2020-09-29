package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * 字典
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dict {
    private int id;
    private int pid;
    private String value;
    private String label;
    private String type;
    private String description;
    private int sort;
    private char status;


}
