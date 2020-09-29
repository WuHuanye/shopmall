package com.example.demo.utils;

import com.example.demo.vo.SessionUser;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 图片上传（以nginx作为服务器）
 *
 */
public class FtpUtils {
    /**
     * @param file  文件对象
     * @param filename  存储后文件的名称
     * @param imgType 图片的类别，这里指的是图片所属，比如关于头像的，关于商品的 关于系统的三种，通过该属性设置其路径（属于哪个文件夹下）
     *                1：头像  2：商品    3：系统    4、其他
     * @return
     */
    public static String uploadImg(MultipartFile file,String filename,int imgType){
        String http = "http://";
        String uploadDIR = "/home/images/shopmall/";      //上传的根路径
        String host = "";
        int port = 21;
        String ftpUsername =  "";
        String password = "";

        String IMGURLROOT = http+host+"/shopmall/";     //上传后通过浏览器查看的根路径，   eg: IMGURLROOT/header/用户.jpg   查看用户头像

        String folderURL = "";
        String imgUrl = "";
        String folder = "";
        if(file.isEmpty()){
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);  //获得图片后缀名
        if(imgType == 1){   //头像
            folder = "header/";
            folderURL = uploadDIR+"header";
            filename = "header_"+filename+"."+suffix;
        }else if (imgType == 2){    //商品
            folder = "mall/";
            folderURL = uploadDIR+"mall";
            filename = "mall_" + UUID.randomUUID().toString()+"."+suffix;
        }else if (imgType == 3) {    //系统
            folder = "system/";
            folderURL = uploadDIR+"system";
            filename = "sys_"+ UUID.randomUUID().toString()+"."+suffix;
        }else {     //其他
            folder = "others/";
            folderURL = uploadDIR+"other";
            filename = "others_" + UUID.randomUUID().toString()+"."+suffix;
        }
        //1、连接ftp服务器
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host,port);
            //2、登录ftp服务器:username为wuhuanye时，传到/home/wuhuanye/文件夹下，username为ftpuser时，上传到/home/ftpuser/
            ftpClient.login(ftpUsername, password);
            File tagFile = new File(folderURL);
            if(!tagFile.exists()){
                ftpClient.makeDirectory(folderURL);
            }
            boolean checkStatus = ftpClient.changeWorkingDirectory(folderURL);//切换目录
//            System.out.println("目录切换：："+checkStatus);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  //2）指定文件类型(重要！！！！！)
            InputStream inputStream = file.getInputStream();    //获得文件流
            ftpClient.storeFile(filename, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ftpClient.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imgUrl = IMGURLROOT + folder + filename;
        return imgUrl;
    }

    /*多图上传*/
    public static String uploadManyImg(MultipartFile[] file,int imgType, HttpServletRequest request){
        String http = "http://";
        String uploadDIR = "/home/images/shopmall/";      //上传的根路径
        String host = "";
        int port = 21;
        String ftpUsername =  "";
        String password = "";

        String IMGURLROOT = http+host+"/shopmall/";     //上传后通过浏览器查看的根路径，   eg: IMGURLROOT/header/用户.jpg   查看用户头像

        String folderURL = "";
        String imgUrl = "";
        String folder = "";

        String filename = "";
        List<String> urlList = new ArrayList<>();
        if(file!=null){     //执行上传事件
            //1、连接ftp服务器
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(host,port);
                //2、登录ftp服务器:username为wuhuanye时，传到/home/wuhuanye/文件夹下，username为ftpuser时，上传到/home/ftpuser/
                ftpClient.login(ftpUsername, password);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  //2）指定文件类型(重要！！！！！)
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(MultipartFile mfile : file){
//                System.out.println("循环");
                String originalFilename = mfile.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);  //获得图片后缀名
                if(imgType == 1){   //头像
                    folder = "header/";
                    folderURL = uploadDIR+"header";
                    filename = "header_"+filename+"."+suffix;
                }else if (imgType == 2){    //商品
                    folder = "mall/";
                    folderURL = uploadDIR+"mall";
                    filename = "mall_" + UUID.randomUUID().toString()+"."+suffix;
                }else if (imgType == 3) {    //系统
                    folder = "system/";
                    folderURL = uploadDIR+"system";
                    filename = "sys_"+ UUID.randomUUID().toString()+"."+suffix;
                }else {     //其他
                    folder = "others/";
                    folderURL = uploadDIR+"other";
                    filename = "others_" + UUID.randomUUID().toString()+"."+suffix;
                }
                File tagFile = new File(folderURL);
                if(!tagFile.exists()){
                    try {
                        ftpClient.makeDirectory(folderURL);
                        boolean checkStatus = ftpClient.changeWorkingDirectory(folderURL);  //切换目录
                        InputStream inputStream = mfile.getInputStream();    //获得文件流
                        ftpClient.storeFile(filename, inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return IMGURLROOT + folder + filename;

    }

    /*--——————————————————————上面两种方式是在本地上传到远程服务器上时采用——————————————*/
    /*————————————————下面这种方式是项目部署到服务器之后使用————————————————*/
    /**
     *
     * @param file
     * @param imgType 图片什么类型 header mall sys..
     * @param request
     * @param sysType   如果是系统图片的话，判断是logo还是联系我（link）
     * @注意：windows上传路径格式和linux不一致，记得改 windows:\\    linux:/
     * @return
     */
    public static String uploadImg2(MultipartFile[] file,int imgType, HttpServletRequest request,String sysType){
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");
        /*访问*/
        String http = "http://";
        String host = "";
        String IMGURLROOT = http+host+"/shopmall";     //上传后通过浏览器查看的根路径，   eg: IMGURLROOT/header/用户.jpg   查看用户头像
        String categoryNameFolder = ""; //访问时的文件夹

        /*上传*/
//        String uploadBaseDir = "D:\\atest"; //图片上传根最基本路径(windows格式)
        String uploadBaseDir = "/home/images/shopmall/";
        String uploadCategoryDir = "";   //图片上传分类路径 header mall sys ...
        String imgPath = "";    //图片路径
        String filename = "";

        for(MultipartFile mfile : file) {
            String originalFilename = mfile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);  //获得图片后缀名
            if (imgType == 1) {   //头像
//                System.out.println("头像");
                categoryNameFolder = "/header/";
                filename = "header_" + user.getUserId() + "." + suffix;
            } else if (imgType == 2) {    //商品
                categoryNameFolder = "/mall/";
                filename = "mall_" + UUID.randomUUID().toString() + "." + suffix;
            } else if (imgType == 3) {    //系统
                if (sysType.equals("logo")){
                    filename = "sys_" + "LOGO" + "." + "jpg";
                }else {//联系我
                    filename = "sys_" + "LINK" + "." + "jpg";
                }
                categoryNameFolder = "/sys/";
            } else {     //其他
                categoryNameFolder =  "/other/";
                filename = "others_" + UUID.randomUUID().toString() + "." + suffix;
            }
            uploadCategoryDir = uploadBaseDir + categoryNameFolder;
            File newFile = new File(uploadCategoryDir);
            if (!newFile.exists()){
//                System.out.println("不存在，创建文件夹");
                newFile.mkdir();
            }
            //通过CommonsMultipartFile的方法直接写文件(注意这个时候）
            try {
                imgPath = uploadCategoryDir + filename;
                File imgFile = new File(imgPath);   //上传的路径
                mfile.transferTo(imgFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return IMGURLROOT + categoryNameFolder+filename;    //http访问的路径
    }


    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     *  http://139.199.157.189/shopmall/mall/mall_00099820-0735-4dd6-8c85-4a292ea891ae.png
     *   服务器图片根路径 /home/images/shopmall/
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = null;
        file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
