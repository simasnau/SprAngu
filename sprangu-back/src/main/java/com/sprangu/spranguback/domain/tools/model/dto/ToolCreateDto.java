package com.sprangu.spranguback.domain.tools.model.dto;

import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ToolCreateDto {

    private String name;
    private String description;
    private Long ownerId;
    private Integer hourlyPrice;
    private Integer dailyPrice;
    private ToolTypeEnum toolType;
    private List<String> imageContent;

}
