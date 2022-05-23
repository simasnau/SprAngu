package com.sprangu.spranguback.domain.tools.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RentStartDto {

    private Date rentEndDate;
    private Long userId;

}
