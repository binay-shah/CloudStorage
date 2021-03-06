package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addNote(Authentication authentication, Note note, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        note.setUserId(userId);
        try{
        Integer count = this.noteService.addOrUpdateNote(note);
        if(count > 0 ){
            model.addAttribute("success", true);
        }else
            model.addAttribute("error", true);


        }catch (Exception e){
            model.addAttribute("success", false);
            model.addAttribute("errorType", 1);
            model.addAttribute("errorMessage", "Note can't be saved as description exceed 1000 characters");
        }
        return "result";
    }

    @PostMapping("/{noteId}")
    public String deleteNote(@PathVariable("noteId") String noteId, Note note, Model model) {
        Integer id = Integer.parseInt(noteId);
        Integer count = this.noteService.deleteNote(id);
        if(count > 0 ){
            model.addAttribute("success", true);
        }else
            model.addAttribute("error", true);

        return "result";
    }
}
