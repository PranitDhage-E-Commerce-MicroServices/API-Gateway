package com.apigateway.security;

import com.apigateway.exceptions.AuthenticationException;
import com.apigateway.service.IUserClient;
import com.apigateway.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private IUserClient userClient;

    @Autowired
    private JwtService jwtService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    // REST call to AUTH service
//                    Boolean validJwt = userClient.validateJwt(authHeader);
                    jwtService.validateToken(authHeader);

                } catch (Exception e) {
                    log.info("invalid access...! - {}", e.getMessage());
                    throw new AuthenticationException("Unauthorized Access to Application", Constants.ERR_AUTHENTICATION);
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}