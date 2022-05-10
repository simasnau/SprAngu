package com.sprangu.spranguback.application.utils;

import com.sprangu.spranguback.domain.user.model.security.UserDetailed;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.security.auth.login.CredentialException;

public class SecurityUtils {

    @SneakyThrows
    public static void checkAccess(Long userId) {
        var user = (UserDetailed) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || !user.getId().equals(userId)) {
            throw new CredentialException("Wrong user!");
        }
    }
}
