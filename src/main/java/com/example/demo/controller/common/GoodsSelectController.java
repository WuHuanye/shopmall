package com.example.demo.controller.common;

import com.example.demo.pojo.Dict;
import com.example.demo.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二级联动
 */
@Controller
public class GoodsSelectController {
    @Autowired
    DictService dictService;
    //加载第二级菜单
    @RequestMapping("/admin/goods/listForSelect")
    @ResponseBody
    public List<Dict> listForSelect(int dictId, Model model){
//        System.out.println(dictId);
        //加载字典
        List<Dict> chilDicts = dictService.chilerentListById(dictId);
        return chilDicts;
    }
}
