package com.sprangu.spranguback.domain.tools.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RentStartDto {

    private LocalDateTime rentEndDate;
    private Long userId;

}
