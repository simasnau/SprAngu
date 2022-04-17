package com.sprangu.spranguback.domain.user.repository;


import com.sprangu.spranguback.domain.user.model.dto.UserInfo;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser_;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final EntityManager em;

    @Override
    public UserInfo loginUser(String name, String password) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(UserInfo.class);
        var root = cq.from(RegisteredUser.class);
        cq.multiselect(root.get(RegisteredUser_.id), root.get(RegisteredUser_.name));
        cq.where(
                cb.equal(root.get(RegisteredUser_.name), name),
                cb.equal(root.get(RegisteredUser_.password), password)
        );
        return em.createQuery(cq).getSingleResult();
    }

    @SneakyThrows
    @Override
    public void existsWithName(String name) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(String.class);
        var root = cq.from(RegisteredUser.class);
        cq.multiselect(root.get(RegisteredUser_.name));
        cq.where(
                cb.equal(root.get(RegisteredUser_.name), name)
        );
        if (!em.createQuery(cq).getResultList().isEmpty()) {
            throw new Exception("User exists with name " + name);
        }
    }
}
