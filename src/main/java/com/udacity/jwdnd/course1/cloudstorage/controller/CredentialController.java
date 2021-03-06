package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrUpdateCredential(Authentication authentication, Credential credential, Model model) {
        credential.setUserId(userService.getUser(authentication.getName()).getUserId());
        Integer count = this.credentialService.addOrUpdateCredential(credential);
        if(count > 0 ){
            model.addAttribute("success", true);
        }else
            model.addAttribute("error", true);

        return "result";
    }

    @PostMapping("/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") String credentialId,  Credential credential, Model model) {
        Integer id = Integer.parseInt(credentialId);
        Integer count = this.credentialService.deleteCredential(id);
        if(count > 0 ){
            model.addAttribute("success", true);
        }else
            model.addAttribute("error", true);

        return "result";
    }
}
