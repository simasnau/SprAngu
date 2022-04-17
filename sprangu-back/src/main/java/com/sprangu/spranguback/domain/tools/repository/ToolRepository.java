package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long>, ToolRepositoryCustom {

}
