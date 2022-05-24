package com.sprangu.spranguback.domain.tools.model.dto;

import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;

import java.sql.Timestamp;
import java.util.Date;
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
    private Date rentStartDate;
    private Date originalEndDate;
    private Date realEndDate;
    private Integer hourlyPrice;
    private Integer dailyPrice;

    public static ToolRentInfoDto of(ToolRentInfo toolRentInfo) {
        Hibernate.initialize(toolRentInfo.getRentedTool());

        Tool rentedTool = toolRentInfo.getRentedTool();
        Hibernate.initialize(rentedTool.getImages());
        Hibernate.initialize(toolRentInfo.getUser());

        return ToolRentInfoDto.builder()
                .id(toolRentInfo.getId())
                .toolId(rentedTool.getId())
                .toolName(rentedTool.getName())
                .toolDescription(rentedTool.getDescription())
                .toolType(rentedTool.getToolType())
                .imageContent(rentedTool.getImages())
                .owner(rentedTool.getOwner())
                .userName(toolRentInfo.getUser().getName())
                .rentStartDate(toolRentInfo.getStartDate() == null ? null : Timestamp.valueOf(toolRentInfo.getStartDate()))
                .originalEndDate(toolRentInfo.getOriginalEndDate() == null ? null : Timestamp.valueOf(toolRentInfo.getOriginalEndDate()))
                .realEndDate(toolRentInfo.getRealEndDate() == null ? null : Timestamp.valueOf(toolRentInfo.getRealEndDate()))
                .hourlyPrice(toolRentInfo.getHourlyPrice())
                .dailyPrice(toolRentInfo.getDailyPrice())
                .build();
    }
}
