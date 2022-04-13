package com.sprangu.spranguback.domain.user.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserInfo {
    private Long id;
    private String name;
    private String password;

    public UserInfo(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UserInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
