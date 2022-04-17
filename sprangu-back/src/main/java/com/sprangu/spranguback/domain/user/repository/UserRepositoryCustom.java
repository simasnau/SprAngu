package com.sprangu.spranguback.domain.user.repository;

import com.sprangu.spranguback.domain.user.model.dto.UserInfo;

public interface UserRepositoryCustom {
    UserInfo loginUser(String name, String password);

    void existsWithName(String name);
}
