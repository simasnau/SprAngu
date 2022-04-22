package com.sprangu.spranguback.domain.tools.model;

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
    private List<RegisteredUser> allUsers = new ArrayList<>();
    private RegisteredUser currentUser;
    private Integer hourlyPrice;
    private Integer dailyPrice;
    private ToolTypeEnum toolType;
    private String imageContent;

    public static ToolDto of(Tool tool) {
        return ToolDto.builder()
                .id(tool.getId())
                .name(tool.getName())
                .description(tool.getDescription())
                .owner(tool.getOwner())
                .currentUser(tool.getCurrentUser())
                .hourlyPrice(tool.getHourlyPrice())
                .dailyPrice(tool.getDailyPrice())
                .toolType(tool.getToolType())
                .imageContent(tool.getImageContent())
                .build();
    }
}
