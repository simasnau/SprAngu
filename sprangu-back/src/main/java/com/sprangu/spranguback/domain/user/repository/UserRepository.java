package com.sprangu.spranguback.domain.user.repository;

import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<RegisteredUser, Long>, UserRepositoryCustom {

}
