package com.sprangu.spranguback.domain.user;

import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserInfo userInfo) {
        userService.registerUser(userInfo);
    }

    @GetMapping("/login")
    public String loginUser(@RequestParam String name, @RequestParam String password) throws CredentialException {
        return userService.loginUser(name, password);
    }

}
