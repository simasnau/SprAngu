package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.ToolsFilter;
import com.sprangu.spranguback.domain.tools.model.dto.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.model.entity.Tool_;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser_;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ToolRepositoryImpl implements ToolRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<ToolBasicDto> getBasicToolView() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ToolBasicDto> query = cb.createQuery(ToolBasicDto.class);

        Root<Tool> root = query.from(Tool.class);
        query.multiselect(
                root.get(Tool_.id),
                root.get(Tool_.name),
                root.get(Tool_.description),
                root.get(Tool_.hourlyPrice),
                root.get(Tool_.dailyPrice),
                root.get(Tool_.visible));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Tool> searchTools(ToolsFilter toolsFilter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tool> query = cb.createQuery(Tool.class);

        Root<Tool> root = query.from(Tool.class);
        List<Predicate> predicatesList = new ArrayList<>();
        if (toolsFilter.getName() != null && !toolsFilter.getName().isBlank()) {
            predicatesList.add(cb.like(cb.upper(root.get(Tool_.name)), "%" + toolsFilter.getName().toUpperCase() + "%"));
        }
        if (toolsFilter.getMaxDailyPrice() != 0) {
            predicatesList.add(cb.lt(root.get(Tool_.dailyPrice), toolsFilter.getMaxDailyPrice()));
        }
        if (toolsFilter.getMinDailyPrice() != 0) {
            predicatesList.add(cb.gt(root.get(Tool_.dailyPrice), toolsFilter.getMinDailyPrice()));
        }
        if (toolsFilter.getMaxHourlyPrice() != 0) {
            predicatesList.add(cb.lt(root.get(Tool_.hourlyPrice), toolsFilter.getMaxHourlyPrice()));
        }
        if (toolsFilter.getMinHourlyPrice() != 0) {
            predicatesList.add(cb.gt(root.get(Tool_.hourlyPrice), toolsFilter.getMinHourlyPrice()));
        }
        if (toolsFilter.getToolType() != null) {
            predicatesList.add(cb.equal(root.get(Tool_.toolType), toolsFilter.getToolType()));
        }
        predicatesList.add(
                cb.or(
                        cb.isNull(root.get(Tool_.removed)),
                        cb.isFalse(root.get(Tool_.removed))
                )
        );
        Predicate[] predicatesArray = predicatesList.toArray(new Predicate[0]);
        query.select(root).where(predicatesArray);

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Tool> getAllUserToolsByUserId(Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tool> query = cb.createQuery(Tool.class);
        Root<Tool> root = query.from(Tool.class);

        query.select(root);

        query.where(cb.equal(root.get(Tool_.owner).get(RegisteredUser_.id), userId),
                cb.or(
                        cb.isNull(root.get(Tool_.removed)),
                        cb.isFalse(root.get(Tool_.removed))
                ));
        return em.createQuery(query).getResultList();
    }
}
