package com.sprangu.spranguback.domain.user;

import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(UserInfo userInfo) {
        userRepository.existsWithName(userInfo.getName()); // TODO delete later
        var user = RegisteredUser.builder()
                .name(userInfo.getName())
                .password(userInfo.getPassword())
                .build();
        userRepository.save(user);
    }

    public UserInfo loginUser(String name, String password) {
        var user = userRepository.loginUser(name, password);
        if (user == null) {
            return UserInfo.builder()
                    .id(0L)
                    .build();
        }
        return user;
    }
}
