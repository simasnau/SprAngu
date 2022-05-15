package com.sprangu.spranguback.domain.tools.model.dto;

import com.sprangu.spranguback.application.utils.ImageUtils;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

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
    private String image;

    public static ToolBasicDto of(Tool tool) {
        Hibernate.initialize(tool.getImages());
        return ToolBasicDto.builder()
                .id(tool.getId())
                .name(tool.getName())
                .description(tool.getDescription())
                .hourlyPrice(tool.getHourlyPrice())
                .dailyPrice(tool.getDailyPrice())
                .image(ImageUtils.resizeToThumbnails(tool.getImages().isEmpty() ? null : tool.getImages().get(0)))
                .build();
    }
}
