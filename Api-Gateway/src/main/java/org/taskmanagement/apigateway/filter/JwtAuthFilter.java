package org.taskmanagement.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.taskmanagement.apigateway.exception.custom.TokenIsMissing;
import org.taskmanagement.apigateway.service.JwtService;

import java.util.Objects;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    private final JwtService jwtService;
    public JwtAuthFilter(@Autowired JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String  authorization = request.getHeaders().getFirst("Authorization");
            if(authorization == null || !authorization.startsWith("Bearer ")){
                throw new TokenIsMissing("Bearer Token is required");
            }
            String token = authorization.substring(7);
            String email = jwtService.getUserName(token);
            System.out.println("JWT Token: " + email);

            return  chain.filter(exchange);
        });
    }
    public static class Config {

    }
}
