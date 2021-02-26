package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM Files")
    List<File> getAllFiles();

    @Select("SELECT * FROM File WHERE fileId = #{id}")
    File findFile(Integer id);

    @Insert("INSERT INTO Note (filename, contenttype, filesize, userid, filedata) VALUES( #{fileName}), #{contentType}, #{fileSize}, #{userID}, #{fileData}")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("DELETE FROM File WHERE fileId = #{fileId}")
    Integer delete(Integer fileId);
}
