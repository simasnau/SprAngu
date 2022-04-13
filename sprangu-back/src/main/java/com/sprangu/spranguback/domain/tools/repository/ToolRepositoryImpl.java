package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.model.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.Tool_;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
}
