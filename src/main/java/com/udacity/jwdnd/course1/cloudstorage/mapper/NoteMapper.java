package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM Notes")
    List<Note> getAllNotes();

    @Select("SELECT * FROM Notes WHERE noteid = #{id}")
    Note findNote(Integer id);

    @Insert("INSERT INTO Notes (notetitle, notedescription, userid) VALUES( #{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer addNote(Note note);

    @Update("UPDATE notes set notetitle = #{noteTitle}, notedescription = #{noteDescription} where noteid = #{noteId}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM Notes WHERE noteid = #{noteId}")
    Integer delete(Integer noteId);



}
