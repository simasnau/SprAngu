package com.sprangu.spranguback.domain.user;

import com.sprangu.spranguback.application.config.JwtUtils;
import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import com.sprangu.spranguback.domain.user.model.security.UserDetailed;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import javax.transaction.Transactional;
import java.util.Collections;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public UserDetailed getLoggedUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return null;
        }
        return (UserDetailed) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @SneakyThrows
    public void registerUser(UserInfo userInfo) {
        var existingUser = userRepository.getByName(userInfo.getName());
        if (existingUser != null) {
            throw new IllegalArgumentException("User exists with name " + userInfo.getName());
        }
        var user = RegisteredUser.builder()
                .name(userInfo.getName())
                .password(userInfo.getPassword())
                .build();
        userRepository.save(user);
    }

    public String loginUser(String name, String password) throws CredentialException {
        var user = userRepository.loginUser(name, password);
        var userDetailed = loadUserByUsername(user
                .orElseThrow(() -> new CredentialException("Wrong credentials"))
                .getName());
        return createJwtToken(userDetailed);
    }

    public String createJwtToken(UserDetailed userDetailed) {
        return jwtUtils.generateToken(userDetailed);
    }

    @SneakyThrows
    @Override
    public UserDetailed loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.getByName(username);
        var authorities = new SimpleGrantedAuthority(user.getRole());
        return new UserDetailed(user.getId(), user.getName(), "", Collections.singleton(authorities));
    }
}
