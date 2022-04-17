package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.ToolsFilter;
import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.model.ToolBasicDto;

import java.util.List;

public interface ToolRepositoryCustom {

    List<ToolBasicDto> getBasicToolView();
    List<Tool> searchTools(ToolsFilter toolsFilter);
}
