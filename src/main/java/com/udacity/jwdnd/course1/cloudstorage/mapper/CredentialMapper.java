package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM Credentials")
    List<Credential> getAllCredentials();

    @Select("SELECT * FROM Credentials where userid = #{userId}")
    List<Credential> getCredentialsByUserId(Integer userId);

    @Select("SELECT * FROM Credentials WHERE credentialId = #{id}")
    Credential findCredential(Integer id);

    @Insert("INSERT INTO Credentials (url, username, key, password, userid) VALUES( #{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer addCredential(Credential credential);

    @Update("UPDATE credentials set url = #{url}, key = #{key}, password=#{password} where credentialid = #{credentialId}")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM Credentials WHERE credentialid = #{id}")
    Integer delete(Integer id);
}
