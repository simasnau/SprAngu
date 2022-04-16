package com.sprangu.spranguback.domain.tools.repository;


import com.sprangu.spranguback.domain.tools.ToolsFilter;
import com.sprangu.spranguback.domain.tools.model.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ToolRepositoryCustom extends JpaRepository<Tool, Long> {

    List<ToolBasicDto> getBasicToolView();
    List<Tool> searchTools(ToolsFilter toolsFilter);
}
