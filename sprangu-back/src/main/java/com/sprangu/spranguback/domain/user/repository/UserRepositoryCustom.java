package com.sprangu.spranguback.domain.user.repository;

import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepositoryCustom extends JpaRepository<RegisteredUser, Long> {
    UserInfo loginUser(String name, String password);

    void existsWithName(String name);
}
