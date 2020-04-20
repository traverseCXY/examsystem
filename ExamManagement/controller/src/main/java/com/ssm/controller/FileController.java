package com.ssm.controller;

import com.ssm.entity.Message;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.io.File;
import java.io.IOException;

@RestController
public class FileController {

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    @RequestMapping("/imgUpload")
    @ResponseBody
    public Message upTimetable(MultipartFile multipartFile, HttpServletRequest request){
        String filePath = "D:\\SYSTEM\\examsystem\\ExamManagement\\controller\\src\\main\\resources\\static\\resources\\images\\questions";
        String originalFilename = multipartFile.getOriginalFilename();
        int index=originalFilename.lastIndexOf(".");
        String houzhui=originalFilename.substring(index);//获取后缀名
        String newFile=UUID.randomUUID().toString().replace("-","")+houzhui;
        String path = filePath + File.separator + newFile;
        File file = new File(path);
        String titleImg="/images/"+newFile;
        request.setAttribute("titleImg", titleImg);
        Message message=new Message();
        try {
            multipartFile.transferTo(file);
            message.setMessageInfo(titleImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
