package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM Files")
    List<File> getAllFiles();

    @Select("SELECT * FROM Files where userid = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM Files WHERE fileId = #{id}")
    File findFile(Integer id);

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) VALUES( #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("DELETE FROM Files WHERE fileid = #{fileId}")
    Integer delete(Integer fileId);
}
