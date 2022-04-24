package com.sprangu.spranguback.domain.tools.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ToolBasicDto {

    private Long id;
    private String name;
    private String description;
    private Integer hourlyPrice;
    private Integer dailyPrice;
    private boolean visible;

}
