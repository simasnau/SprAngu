package com.sprangu.spranguback.domain.tools.model.dto;

import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ToolRentInfoDto {

    private Long id;
    private Long toolId;
    private String toolName;
    private String toolDescription;
    private ToolTypeEnum toolType;
    private List<String> imageContent;
    private RegisteredUser owner;
    private String userName;
    private LocalDateTime rentStartDate;
    private LocalDateTime originalEndDate;
    private LocalDateTime realEndDate;
    private Integer hourlyPrice;
    private Integer dailyPrice;

}
