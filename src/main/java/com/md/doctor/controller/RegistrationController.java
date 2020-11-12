package com.md.doctor.controller;

import com.md.doctor.dto.ResetPasswordDto;
import com.md.doctor.dto.UserDto;
import com.md.doctor.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@Validated
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    void registerNewUserAccount(@RequestBody UserDto userDto) {
        userService.registerNewUserAccount(userDto);
    }

    @GetMapping("/confirm")
    ResponseEntity<String> confirmAccount(@RequestParam String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            userService.confirmAccount(token);
            return new ResponseEntity<>("Your account is active! You can log in!", HttpStatus.OK);
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
