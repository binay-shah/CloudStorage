package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating CredentialService bean");
    }

    public Integer addOrUpdateCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        if(credential.getCredentialId() == null){
            Credential newCredential = new Credential(credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId());
            newCredential.setDecryptedPassword(decryptedPassword);
            return credentialMapper.addCredential(newCredential);
        }else{
            Credential newCredential = new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId());
            return credentialMapper.updateCredential(newCredential);
        }
    }

    public Integer deleteCredential(Integer id) {
        return credentialMapper.delete(id);
    }

    public List<Credential> getAllCredentials() {

        List<Credential> credentials  = credentialMapper.getAllCredentials();
        for(Credential credential: credentials){
            credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return credentials;
    }
}
