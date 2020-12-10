package doctor.controller;

import doctor.config.LoginCredentials;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {
    @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/login")
    public String login(@RequestBody LoginCredentials credentials) {
        return "Hello";

    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/secure")
    public String secured() {
        return "NO ELO";
    }
}
