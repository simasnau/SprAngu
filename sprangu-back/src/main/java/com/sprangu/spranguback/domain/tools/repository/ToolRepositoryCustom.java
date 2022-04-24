package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.ToolsFilter;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.model.dto.ToolBasicDto;

import java.util.List;

public interface ToolRepositoryCustom {

    List<ToolBasicDto> getBasicToolView();

    List<Tool> searchTools(ToolsFilter toolsFilter);

    List<ToolBasicDto> getAllUserToolsById(Long userId);
}
