package com.sprangu.spranguback.domain.user.repository;

import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<UserInfo> loginUser(String name, String password);

    RegisteredUser getByName(String name);
}
