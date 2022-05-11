package com.sprangu.spranguback.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.Cookie;

@AllArgsConstructor
@Getter
public class JwtResponse {
    private String jwtToken;
    private String jwtRefreshToken;
}
