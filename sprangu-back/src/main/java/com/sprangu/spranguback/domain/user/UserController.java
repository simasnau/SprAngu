package com.sprangu.spranguback.domain.user;

import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserInfo userInfo) {
        userService.registerUser(userInfo);
    }

    @GetMapping("/login")
    public UserInfo loginUser(@RequestParam String name, @RequestParam String password) {
        return userService.loginUser(name, password);
    }


}
