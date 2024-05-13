package com.apigateway.service;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(url = "http://localhost:8081", value = "User-Client")
@FeignClient(name = "USER-SERVICE")
public interface IUserClient {

    @GetMapping(
            value = "/auth/validate-jwt",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Boolean validateJwt(
            @Parameter(description = "JWT Token", required = true) @RequestParam(value = "token", required = true) String token
    );

}
