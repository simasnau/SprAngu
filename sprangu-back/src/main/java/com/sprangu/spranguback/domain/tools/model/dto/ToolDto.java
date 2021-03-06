package com.sprangu.spranguback.domain.tools.model.dto;

import com.sprangu.spranguback.application.utils.ImageUtils;
import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ToolDto {
    private Long id;
    private String name;
    private String description;
    private RegisteredUser owner;
    private RegisteredUser currentUser;
    private Long currentRentId;
    private Date rentEndDate;
    private Integer hourlyPrice;
    private Integer dailyPrice;
    private ToolTypeEnum toolType;
    private List<String> imageContent;
    private boolean visible;
    private Integer version;

    public static ToolDto of(Tool tool, CurrentRentInfo currentRentInfo, boolean rawImages) {
        Hibernate.initialize(tool.getImages());
        return ToolDto.builder()
                .id(tool.getId())
                .name(tool.getName())
                .description(tool.getDescription())
                .owner(tool.getOwner())
                .currentUser(currentRentInfo == null ? null : currentRentInfo.getCurrentUser())
                .currentRentId(currentRentInfo == null ? null : currentRentInfo.getRentId())
                .rentEndDate(currentRentInfo == null ? null : Timestamp.valueOf(currentRentInfo.getRentEndDate()))
                .hourlyPrice(tool.getHourlyPrice())
                .dailyPrice(tool.getDailyPrice())
                .toolType(tool.getToolType())
                .imageContent(rawImages ? tool.getImages() : ImageUtils.resizeToThumbnails(tool.getImages()))
                .visible(Boolean.TRUE.equals(tool.getVisible()))
                .version(tool.getVersion())
                .build();
    }
}
