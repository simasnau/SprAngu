package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.ToolsFilter;
import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.model.ToolShortView;

import java.util.List;

public interface ToolRepositoryCustom {

    List<ToolShortView> getBasicToolView();

    List<Tool> searchTools(ToolsFilter toolsFilter);

    List<ToolShortView> getAllUserToolsById(Long userId);
}
