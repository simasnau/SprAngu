package com.sprangu.spranguback.domain.user;

import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(UserInfo userInfo) {
        var user = RegisteredUser.builder()
                .name(userInfo.getName())
                .password(userInfo.getPassword())
                .build();
        userRepository.save(user);
    }
}
