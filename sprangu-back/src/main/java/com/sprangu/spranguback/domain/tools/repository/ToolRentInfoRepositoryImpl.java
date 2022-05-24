package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.model.dto.ToolRentInfoDto;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo_;
import com.sprangu.spranguback.domain.tools.model.entity.Tool_;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser_;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<ToolRentInfoDto> getToolRentInfoForUser(@NonNull Long userId, boolean onlyActive) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ToolRentInfo> query = cb.createQuery(ToolRentInfo.class);
        Root<ToolRentInfo> root = query.from(ToolRentInfo.class);
        query.select(root);

        query.orderBy(cb.desc(root.get(ToolRentInfo_.startDate)));

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get(ToolRentInfo_.user).get(RegisteredUser_.id), userId));

        if (onlyActive) {
            predicates.add(cb.isNull(root.get(ToolRentInfo_.realEndDate)));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getResultList().stream().map(ToolRentInfoDto::of).collect(Collectors.toList());
    }
}
