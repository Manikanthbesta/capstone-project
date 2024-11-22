package com.Group1.authentication.Service;

import com.Group1.authentication.Model.authentication;
import com.Group1.authentication.Repository.authenticationRepo;
import com.Group1.authentication.dto.authdto;
import com.Group1.authentication.feign.userClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class authenticationService {

    @Autowired
    private authenticationRepo authrepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private userClient userclient;

    public authentication register(authdto auth) {
        authentication a = new authentication();
        a.setUserName(auth.getUserName());
        a.setRole(auth.getRole());
        a.setPassword(passwordEncoder.encode(auth.getPassword()));
        authentication b = authrepo.save(a);
        userclient.create(b.getUserId());
        return b;
    }

}
