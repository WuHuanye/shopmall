package com.example.demo.controller.common;

import com.example.demo.utils.FtpUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class FtpController {
    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String,Object> uploadImg(@RequestParam(value = "projectImg",required = true) MultipartFile file) throws IOException {
        String http = "http://";
        String imgFile = "/home/images/shopmall/";      //上传的根路径
        String host = "139.199.157.189";
        int port = 21;
        String ftpUsername =  "ftpuser";
        String password = "wuhuanye";
        Map<String,Object> map = new HashMap<>();
        //将图片上传到服务器
        if(file.isEmpty()){
            return null;
        }
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //图片名称为uuid+图片后缀防止冲突
        String fileName = UUID.randomUUID().toString()+"."+suffix;
        //1、连接ftp服务器
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host,port);
        //2、登录ftp服务器:username为wuhuanye时，传到/home/wuhuanye/文件夹下，username为ftpuser时，上传到/home/ftpuser/
        ftpClient.login(ftpUsername, password);
        File tagFile = new File(imgFile);
        //如果目录不存在，创建目录
        if(!tagFile.exists()){
            ftpClient.makeDirectory(imgFile);
        }
        ftpClient.changeWorkingDirectory(imgFile);  //切换目录
        //2）指定文件类型(重要！！！！！)
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

        InputStream inputStream = file.getInputStream();    //获得文件流
        boolean flag = ftpClient.storeFile(fileName, inputStream);
        if (flag){
            map.put("code",0);
            map.put("data",http+host+"/shopmall/"+fileName);
            map.put("msg","上传图片成功");
            ftpClient.logout();
            return map;
        }
        return map;
    }
    /**
     * 多图
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/uploadImg1")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile[] file, HttpServletRequest request){
        String imgUrl = FtpUtils.uploadManyImg(file,  2, request);      //此路上传的都是到mall文件夹下
        System.out.println(imgUrl);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("data",imgUrl);
        map.put("msg","上传图片成功");
        return map;
    }
/*--——————————————————————上面两种方式是在本地上传到远程服务器上时采用——————————————*/
    /*————————————————下面这种方式是项目部署到服务器之后使用:上传速度很快————————————————*/
    /*transfer to上传*/
    @RequestMapping("/uploadImg2")
    @ResponseBody
    public Map<String,Object> a(MultipartFile[] file, HttpServletRequest request){
        int imgType = 2;  //默认通过此接口上传的图片暂时都放到mall文件夹下
        String type = "";   //w文件类型
        String sysType = "";    //如果是存放在sys中，是LOGO还是LINK
        try {
            type = request.getParameter("type");
            sysType = request.getParameter("sysType");  //判断是logo还是我们图片
            if (type.equals("sys")){
                imgType = 3;    //存放到sys文件夹
            }
        }catch (Exception e){
            imgType = 2;
            sysType = "";
        }
        String imgPath = FtpUtils.uploadImg2(file, imgType, request,sysType);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("data",imgPath);
        map.put("msg","上传图片成功");
        return map;
    }
}
