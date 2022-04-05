package com.sprangu.spranguback.domain.tools.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolBasicDto {

    private Long id;
    private String name;
    private String description;
    private String ownerName;
    private Integer hourlyPrice;
    private Integer dailyPrice;

}
