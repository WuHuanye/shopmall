package com.example.demo;

import java.io.File;

public class FIleTest {
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
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
    // http://139.199.157.189/shopmall/mall/mall_00099820-0735-4dd6-8c85-4a292ea891ae.png
    //服务器图片根路径 /home/images/shopmall/
   /* public static void main(String[] args) {
        String path = "D:\\home\\images\\8740b28d-984c-476f-a54d-879f46bcaede.png";
        System.out.println(deleteFile(path));
    }*/
}
