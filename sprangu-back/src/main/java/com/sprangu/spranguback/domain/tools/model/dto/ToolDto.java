package com.sprangu.spranguback.domain.tools.model.dto;

import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(staticName = "of")
public class ToolDto {
    private Long id;
    private String name;
    private String description;
    private RegisteredUser owner;
    private RegisteredUser currentUser;
    private Integer hourlyPrice;
    private Integer dailyPrice;
    private ToolTypeEnum toolType;
    private String imageContent;

    public static ToolDto of(Tool tool, RegisteredUser currentUser) {
        return ToolDto.builder()
                .id(tool.getId())
                .name(tool.getName())
                .description(tool.getDescription())
                .owner(tool.getOwner())
                .currentUser(currentUser)
                .hourlyPrice(tool.getHourlyPrice())
                .dailyPrice(tool.getDailyPrice())
                .toolType(tool.getToolType())
                .imageContent(tool.getImageContent())
                .build();
    }
}
