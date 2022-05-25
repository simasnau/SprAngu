package com.sprangu.spranguback.domain.tools.model.dto;

import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CurrentRentInfo {

    private Long rentId;
    private RegisteredUser currentUser;
    private LocalDateTime rentEndDate;

}
