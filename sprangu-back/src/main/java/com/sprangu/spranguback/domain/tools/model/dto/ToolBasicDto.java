package com.sprangu.spranguback.domain.tools.model.dto;

import com.sprangu.spranguback.application.utils.ImageUtils;
import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ToolBasicDto {

    private Long id;
    private String name;
    private String description;
    private Integer hourlyPrice;
    private Integer dailyPrice;
    private boolean visible;
    private List<String> imageContent;
    private Long ownerId;
    private ToolTypeEnum toolType;
    private Integer version;

    public static ToolBasicDto of(Tool tool, Long ownerId, boolean rawImages) {
        Hibernate.initialize(tool.getImages());
        return ToolBasicDto.builder()
                .id(tool.getId())
                .name(tool.getName())
                .description(tool.getDescription())
                .hourlyPrice(tool.getHourlyPrice())
                .dailyPrice(tool.getDailyPrice())
                .visible(tool.isVisible())
                .imageContent(tool.getImages() == null
                        ? null
                        : rawImages
                            ? ImageUtils.resizeToThumbnails(tool.getImages())
                            : tool.getImages())
                .ownerId(ownerId)
                .version(tool.getVersion())
                .toolType(tool.getToolType())
                .build();
    }
}
