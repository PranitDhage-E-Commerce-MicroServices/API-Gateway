package com.apigateway.service;

import com.apigateway.utils.Constants;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignClientRequestInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header(Constants.TOKEN_HEADER, MDC.get(Constants.TOKEN_HEADER));
        };
    }
}
