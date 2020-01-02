package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    @Autowired
    private FastFileStorageClient client;

    public String uploadImage(MultipartFile file) {
        String url = null;
        //创建file对象
//        File f = new File("F:\\upload");
//        if(!f.exists()){
//            //创建文件夹
//            f.mkdir();
//        }
//        //保存文件
//        try {
//            file.transferTo(new File(f,file.getOriginalFilename()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "http://image.leyou.com/"+file.getOriginalFilename();

//        StorePath storePath = this.storageClient.uploadFile(
//                new FileInputStream(file), file.length(), "png", null);
        String filename = file.getOriginalFilename();
        //获取图片后缀
        String afterName = StringUtils.substringAfter(filename, ".");
        try {
            //上传
            StorePath storePath = client.uploadFile(file.getInputStream(), file.getSize(), afterName, null);
            String fullPath = storePath.getFullPath();
            url = "http://image.leyou.com/"+fullPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
