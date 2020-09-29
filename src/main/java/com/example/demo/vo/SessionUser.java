package com.example.demo.vo;

import com.example.demo.pojo.Dict;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 存放头部的展示  包括用户的个别信息和头部logo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser {
    private int userId;
    private String username;
    private String headUrl;
    private int roleId;     //用户角色
    private String nickName;
    private String email;
    private String address;
    private int sex;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String logoUrl; //头部logo  Url
    private String linkUrl; //底部联系我图片 url

    private Map<String, List<Dict>> dictMap;     //头部下拉菜单字典
}
