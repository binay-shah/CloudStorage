package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileService bean");
    }

    public File findFile(Integer fileId) {
        return fileMapper.findFile(fileId);
    }

    public boolean addFile(MultipartFile multipartFile, Integer userId)  {
        File file = new File();

        try {
            file.setUserId(userId);
            file.setFileName(multipartFile.getOriginalFilename());
            file.setContentType(multipartFile.getContentType());
            file.setFileSize("" + multipartFile.getSize());
            file.setFileData(multipartFile.getBytes());
            fileMapper.insert(file);
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }

    public Integer deleteFile(Integer id) {
        return fileMapper.delete(id);
    }

    public List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public List<File> getFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId( userId);
    }
}
