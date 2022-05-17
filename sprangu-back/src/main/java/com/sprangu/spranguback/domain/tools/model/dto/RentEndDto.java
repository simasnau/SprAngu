package com.sprangu.spranguback.domain.tools.model.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class RentEndDto {

    private Double totalPrice;
    private Long daysLate;

}
