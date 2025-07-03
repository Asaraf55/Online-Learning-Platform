package com.example.Api_gateway.service.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateConfig{
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("student-login", r -> r.path("/login")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://student-service")
                )
                .route("student-register", r -> r.path("/register")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://student-service")
                )
                .route("course-service", r -> r.path("/courses/**")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://course-service")
                )
                .route("enrollment-service", r -> r.path("/enrollments/**")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://enrollment-service")
                )
                .route("feedback-service", r -> r.path("/feedback/**")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://feedback-service")
                )
                .build();
    }
}
