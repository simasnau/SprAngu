package com.sprangu.spranguback.application.config;

import com.sprangu.spranguback.application.constants.CookieConstants;
import com.sprangu.spranguback.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;

    public final String[] UNAUTHORIZED_URLS = {
            "/api/user/**",
            "/api/tools/all",
            "/api/tools/search"
    };

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtRequestFilter, SecurityContextHolderAwareRequestFilter.class).authorizeRequests().antMatchers(UNAUTHORIZED_URLS).permitAll()
                .and().cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .authorizeRequests().antMatchers(UNAUTHORIZED_URLS).permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        return source;
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        var allowedMethods = Stream.of(
                        HttpMethod.GET,
                        HttpMethod.POST,
                        HttpMethod.PATCH,
                        HttpMethod.DELETE,
                        HttpMethod.PUT
                ).map(HttpMethod::name)
                .collect(Collectors.toList());

        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(allowedMethods);
        corsConfiguration.setMaxAge(600000L);
        corsConfiguration.setExposedHeaders(Collections.singletonList(CookieConstants.AUTHORIZATION));
        return corsConfiguration;
    }
}
