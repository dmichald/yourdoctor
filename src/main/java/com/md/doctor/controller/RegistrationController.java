package com.md.doctor.controller;

import com.md.doctor.dto.OfficeContext;
import com.md.doctor.dto.security.ResetPasswordDto;
import com.md.doctor.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    void registerNewUserAccount(@RequestBody OfficeContext officeContext) {
        userService.registerNewUserAccount(officeContext);
    }

    @GetMapping("/confirm")
    ResponseEntity<String> confirmAccount(@Valid @RequestParam String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            userService.confirmAccount(token);
            return new ResponseEntity<>("Your account is active! You can log in!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/reset-password")
    void resetPassword(@Valid @RequestParam @Email String email) {
        userService.sendResetPasswordToken(email);
    }

    @PostMapping("/reset-password")
    void resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        userService.changeUserPassword(resetPasswordDto);
    }
}
