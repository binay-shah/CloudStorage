package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileUploadController {

    private UserService userService;
    private FileService fileService;

    public FileUploadController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model){
        try {
            InputStream fis = fileUpload.getInputStream();
            File file = new File();
            file.setUserId(userService.getUser(authentication.getName()).getUserId());
            file.setFileName(fileUpload.getOriginalFilename());
            file.setContentType(fileUpload.getContentType());
            file.setFileSize(""+fileUpload.getSize());
            file.setFileData(fileUpload.getBytes());
            fileService.addFile(file);
            model.addAttribute("files", this.fileService.getAllFiles());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "home";
    }
}
