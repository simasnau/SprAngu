package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.model.dto.ToolRentInfoDto;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo_;
import com.sprangu.spranguback.domain.tools.model.entity.Tool_;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser_;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ToolRentInfoRepositoryImpl implements ToolRentInfoRepositoryCustom {

    private final EntityManager em;

    @Override
    public Optional<ToolRentInfo> getNewestRentInfo(@NonNull Long toolId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ToolRentInfo> query = cb.createQuery(ToolRentInfo.class);
        Root<ToolRentInfo> root = query.from(ToolRentInfo.class);

        query.select(root);
        query.orderBy(cb.desc(root.get(ToolRentInfo_.startDate)));
        query.where(cb.equal(root.get(ToolRentInfo_.rentedTool).get(Tool_.id), toolId));

        TypedQuery<ToolRentInfo> typedQuery = em.createQuery(query);
        typedQuery.setMaxResults(1);
        typedQuery.setFirstResult(0);

        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Override
    public List<ToolRentInfoDto> getToolRentInfoForUser(@NonNull Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ToolRentInfoDto> query = cb.createQuery(ToolRentInfoDto.class);
        Root<ToolRentInfo> root = query.from(ToolRentInfo.class);
        Join<ToolRentInfo, Tool> toolJoin = root.join(ToolRentInfo_.rentedTool);
        Join<ToolRentInfo, RegisteredUser> userJoin = root.join(ToolRentInfo_.user);

        query.multiselect(
                root.get(ToolRentInfo_.id),
                root.get(ToolRentInfo_.rentedTool).get(Tool_.id),
                toolJoin.get(Tool_.name),
                toolJoin.get(Tool_.description),
                toolJoin.get(Tool_.toolType),
                toolJoin.get(Tool_.images),
                toolJoin.get(Tool_.owner),
                userJoin.get(RegisteredUser_.name),
                root.get(ToolRentInfo_.startDate),
                root.get(ToolRentInfo_.originalEndDate),
                root.get(ToolRentInfo_.realEndDate),
                root.get(ToolRentInfo_.hourlyPrice),
                root.get(ToolRentInfo_.dailyPrice)
        );
        query.orderBy(cb.desc(root.get(ToolRentInfo_.startDate)));
        query.where(cb.equal(root.get(ToolRentInfo_.user).get(RegisteredUser_.id), userId));

        return em.createQuery(query).getResultList();
    }
}
