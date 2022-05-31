package com.lh.springboot03.utils;

import com.lh.springboot03.controller.ex.*;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UploadFileUtil {
    /*上传文件最大值*/
    public static final int AVATAR_MAX_SIZE = 13 * 1024 * 1024;
    /*限制上传文件的类型*/
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    //String parent = ResourceUtils.getURL("classpath:").getPath() + "static/upload/" + folderName;
    //文件默认放在当前项目的  "static/upload/" + folderName
    public static String saveFile(MultipartFile file, String folderName) throws FileNotFoundException {
//        文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getOriginalFilename() == null){
            throw new FileEmptyException("文件名异常");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制,最大不超过13MB");
        }
        System.out.println("文件大小："+file.getSize());
//        文件类型
        String contentType = file.getContentType();
//        集合包含某个元素，返回true
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
//        上传文件的路径
//        String parent = ResourceUtils.getURL("classpath:").getPath() + "static/upload/" + folderName;
        String parent = new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent() + "/upload/" + folderName + "/";
        if (parent.split("/")[0].equals("file:")){
            parent = parent.substring(5);
        }
        System.out.println("文件存储路径:" + parent);

        File file1 = new File(parent);
        if (!file1.isDirectory()) {
            if (file1.mkdirs()){
                System.out.println("文件夹创建成功");
            }else {
                System.out.println("文件夹创建失败");
            }
        }
        String originalFilename = file.getOriginalFilename();
        System.out.println("OriginalFilename :" + originalFilename);
        int i = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(i);
        String fileName = UUID.randomUUID().toString().toUpperCase() + substring;
        try {
//            FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(file1.toPath()));
            file.transferTo(new File(file1, fileName));
        } catch (FileStateException e) {
            e.printStackTrace();
            throw new FileStateException("文件状态异常 ");//自定义异常
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadIOException("文件读写异常");//系统异常
        }

        System.out.println("文件访问路径" + "/api" + "/upload/" + folderName + "/" + fileName);
        //返回文件保存路径 （/api 是为了方便代理服务器   即带有/api的请求会被代理 解决跨域问题）
        return "/api" + "/upload/" + folderName + "/" + fileName;
    }
}
