package com.sprangu.spranguback.application.config;

import com.sprangu.spranguback.application.inteceptors.LoggingInterceptor;
import com.sprangu.spranguback.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig extends WebMvcConfigurationSupport {

    private final UserService userService;

    @Bean
    LoggingInterceptor getAuthenticationInterceptor() {
        return new LoggingInterceptor(userService);
    }

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(getAuthenticationInterceptor());
    }
}
