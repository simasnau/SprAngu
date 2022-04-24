package com.sprangu.spranguback.domain.tools.repository;


import com.sprangu.spranguback.domain.tools.model.dto.ToolRentInfoDto;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo;

import java.util.List;
import java.util.Optional;

public interface ToolRentInfoRepositoryCustom {

    Optional<ToolRentInfo> getNewestRentInfo(Long toolId);

    List<ToolRentInfoDto> getToolRentInfoForUser(Long userId);
}
