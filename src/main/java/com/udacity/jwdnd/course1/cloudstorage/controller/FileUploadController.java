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
            model.addAttribute("successMessage", true);
        } else
            model.addAttribute("errorMessage", true);

        return "result";
    }

    @PostMapping("/fileUpload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) {
        try {
            InputStream fis = fileUpload.getInputStream();
            File file = new File();
            boolean success = false;
            Integer userId = userService.getUser(authentication.getName()).getUserId();
            file.setUserId(userService.getUser(authentication.getName()).getUserId());
            file.setFileName(fileUpload.getOriginalFilename());
            file.setContentType(fileUpload.getContentType());
            file.setFileSize("" + fileUpload.getSize());
            file.setFileData(fileUpload.getBytes());
            List<File> fileList = fileService.getFilesByUserId(userId);
            for (File fileItem : fileList) {
                if (fileItem.getFileName().equals(fileUpload.getName())) {
                    success = false;

                }
                success = true;
            }
            if (success && fileUpload.getSize() <= 1024 * 1024 * 5) {
                fileService.addFile(file);
                model.addAttribute("successMessage", true);
            } else {
                model.addAttribute("successMessage", false);
            }


        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("successMessage", false);
        }
        return "result";
    }
}
