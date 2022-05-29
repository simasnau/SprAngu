package com.sprangu.spranguback.application.inteceptors;

import com.sprangu.spranguback.domain.user.UserService;
import com.sprangu.spranguback.domain.user.model.security.UserDetailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        String username = "Unauthorized";
        String role = "None";

        try {
            UserDetailed loggedUser = userService.getLoggedUser();
            if (loggedUser != null) {
                username = loggedUser.getUsername();
                SimpleGrantedAuthority simpleGrantedAuthority = (SimpleGrantedAuthority) loggedUser.getAuthorities().toArray()[0];
                role = simpleGrantedAuthority.getAuthority();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("[User: " + username + ", role: " + role + "][" + request.getMethod() + "]" + request.getRequestURI());
    }
}
