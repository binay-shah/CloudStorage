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
        note.setUserId(userService.getUser(authentication.getName()).getUserId());
        Integer count = this.noteService.addOrUpdateNote(note);
        if(count > 0 ){
            model.addAttribute("successMessage", true);
        }else
            model.addAttribute("errorMessage", true);
        //chatForm.setMessageText("");
        model.addAttribute("notes", this.noteService.getAllNotes());
        return "result";
    }

    @PostMapping("/{noteId}")
    public String deleteNote(@PathVariable("noteId") String noteId, Note note, Model model) {
        Integer id = Integer.parseInt(noteId);
        Integer count = this.noteService.deleteNote(id);
        if(count > 0 ){
            model.addAttribute("successMessage", true);
        }else
            model.addAttribute("errorMessage", true);
        model.addAttribute("notes", this.noteService.getAllNotes());
        return "result";
    }
}
