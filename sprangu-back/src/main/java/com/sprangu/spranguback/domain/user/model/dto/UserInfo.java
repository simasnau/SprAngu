package com.sprangu.spranguback.domain.user.model.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserInfo {
    private Long id;
    private String name;
    private String password;
}
