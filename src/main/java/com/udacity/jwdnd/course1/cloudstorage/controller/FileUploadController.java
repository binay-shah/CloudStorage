package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileUploadController {

    private UserService userService;
    private FileService fileService;

    public FileUploadController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("{fileId}")
    public ResponseEntity downloadFile(@PathVariable("fileId") String fileId) {
        File file = fileService.findFile(Integer.parseInt((fileId)));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    @PostMapping("{fileId}")
    public String deleteFile(@PathVariable("fileId") String fileId, Model model) {
        int count = fileService.deleteFile(Integer.parseInt(fileId));
        if (count > 0) {
            model.addAttribute("success", true);
        } else
            model.addAttribute("error", true);

        return "result";
    }

    @PostMapping("/fileUpload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) {


            boolean success = false;
            boolean isDuplicate = false;
            int errorType = 0;
            Integer userId = userService.getUser(authentication.getName()).getUserId();
            List<File> fileList = fileService.getFilesByUserId(userId);

            if(fileUpload.getSize() < 1024 * 1024 * 5){
                for (File file : fileList) {
                    if(fileUpload.getOriginalFilename().equals(file.getFileName())){
                        isDuplicate = true;
                        errorType = 3;
                    }
                }

                if(!isDuplicate && !fileUpload.isEmpty()){
                    success = fileService.addFile(fileUpload, userId);
                    errorType = 0;
                }
            }
            else{
                errorType = 2;
            }


            model.addAttribute("success", success);
            model.addAttribute("errorType", errorType);


        return "result";
    }
}
