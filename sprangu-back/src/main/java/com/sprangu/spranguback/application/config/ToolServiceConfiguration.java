package com.sprangu.spranguback.application.config;

import com.sprangu.spranguback.domain.tools.ToolsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class ToolServiceConfiguration {

    @Value("${tool-service.impl}")
    String strategy;

    private final Map<String, ToolsService> implementations;

    public ToolServiceConfiguration(Set<ToolsService> allImplementations) {
        implementations = new HashMap<>();
        allImplementations.forEach(impl -> implementations.put(impl.getStrategy(), impl));
    }

    @Bean
    public ToolsService toolsService() {
        return implementations.get(strategy);
    }

}
