package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.dto.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolCreateDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolDto;

import java.util.List;

public interface ToolsService {

    List<ToolDto> getAllTools();

    Long create(ToolCreateDto toolCreateDto);

    ToolDto getById(Long id);

    List<ToolDto> searchTools(ToolsFilter toolsFilter);

    List<ToolBasicDto> getAllUserToolsByUserId();

    Boolean deleteTool(Long toolId);

    Boolean changeToolVisibility(Long toolId);

    Long updateTool(ToolDto toolDto);

    String getStrategy();
}
