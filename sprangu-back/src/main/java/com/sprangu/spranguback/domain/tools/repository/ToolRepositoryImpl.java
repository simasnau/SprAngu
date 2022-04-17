package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.ToolsFilter;
import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.model.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.Tool_;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ToolRepositoryImpl extends SimpleJpaRepository<Tool, Long> implements ToolRepositoryCustom {

    private final EntityManager em;

    public ToolRepositoryImpl(EntityManager em) {
        super(Tool.class, em);
        this.em = em;
    }

    @Override
    public List<ToolBasicDto> getBasicToolView() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ToolBasicDto> query = cb.createQuery(ToolBasicDto.class);

        Root<Tool> root = query.from(Tool.class);
        query.multiselect(root.get(Tool_.name), root.get(Tool_.description), root.get(Tool_.hourlyPrice), root.get(Tool_.dailyPrice));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Tool> searchTools(ToolsFilter toolsFilter){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tool> query = cb.createQuery(Tool.class);

        Root<Tool> root = query.from(Tool.class);
        List<Predicate> predicatesList = new ArrayList<>();
        if (toolsFilter.getName() != null && !toolsFilter.getName().isBlank())
        {
            predicatesList.add(cb.between(root.get(Tool_.hourlyPrice),
                    toolsFilter.getMinDailyPrice(), toolsFilter.getMaxDailyPrice()));
        }
        if (toolsFilter.getMaxDailyPrice() != 0)
        {
            predicatesList.add(cb.lt(root.get(Tool_.dailyPrice), toolsFilter.getMaxHourlyPrice()));
        }
        if (toolsFilter.getMinDailyPrice() != 0)
        {
            predicatesList.add(cb.gt(root.get(Tool_.dailyPrice), toolsFilter.getMinHourlyPrice()));
        }
        if (toolsFilter.getMaxHourlyPrice() != 0)
        {
            predicatesList.add(cb.lt(root.get(Tool_.hourlyPrice), toolsFilter.getMaxHourlyPrice()));
        }
        if (toolsFilter.getMinHourlyPrice() != 0)
        {
            predicatesList.add(cb.gt(root.get(Tool_.hourlyPrice), toolsFilter.getMinHourlyPrice()));
        }
        if (toolsFilter.getToolType() != null)
        {
            predicatesList.add(cb.equal(root.get(Tool_.toolType), toolsFilter.getToolType()));
        }
        Predicate[] predicatesArray = predicatesList.toArray(new Predicate[0]);
        query.select(root).where(predicatesArray);

        return em.createQuery(query).getResultList();
    }
}
