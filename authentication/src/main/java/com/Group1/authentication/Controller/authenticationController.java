package com.Group1.authentication.Controller;

import com.Group1.authentication.Model.authentication;
import com.Group1.authentication.Service.authenticationService;
import com.Group1.authentication.Service.jwtService;
import com.Group1.authentication.dto.authdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class authenticationController {

    @Autowired
    private authenticationService authenticationservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private jwtService jwrs;

    @PostMapping("/register")
    public ResponseEntity<authentication> register(@RequestBody authdto auth) {
        return ResponseEntity.ok(authenticationservice.register(auth));
    }

    @PostMapping("/validate/user")
    public String validateUser(@RequestBody authdto auth) {
        System.out.println("user : " + auth);
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(auth.getUserName(), auth.getPassword()));
        System.out.println("authenticated?? : " + authenticate.isAuthenticated());
        if (authenticate.isAuthenticated()) {
            return jwrs.generateToken(auth.getUserName());
        }
        return null;
    }

    @GetMapping("/validate/token")
    public boolean validateToken(@RequestParam("token") String token) {
        jwrs.validateToken(token);
        return true;
    }
}
