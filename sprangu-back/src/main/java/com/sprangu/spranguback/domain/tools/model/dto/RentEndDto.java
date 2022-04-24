package com.sprangu.spranguback.domain.tools.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RentEndDto {

    private Double totalPrice;
    private LocalDateTime originalEndDate;
    private Long daysLate;

}
