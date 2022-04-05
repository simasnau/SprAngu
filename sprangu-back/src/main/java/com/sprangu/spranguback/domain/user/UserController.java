package com.sprangu.spranguback.domain.user;

import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping ("/register")
    public void registerUser(@RequestBody UserInfo userInfo){
        userService.registerUser(userInfo);
    }

}
