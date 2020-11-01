package com.md.doctor.controller;

import com.md.doctor.config.LoginCredentials;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {
    @PostMapping("/login")
    public String login(@RequestBody LoginCredentials credentials) {
        return "Hello";

    }
}
