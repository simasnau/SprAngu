package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolsFilter {
    private String name;
    private int minHourlyPrice;
    private int maxHourlyPrice;
    private int minDailyPrice;
    private int maxDailyPrice;
    private ToolTypeEnum toolType;
}
