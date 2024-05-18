package com.apigateway.service;

import com.apigateway.utils.Constants;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.lang3.StringUtils;

@Configuration
@RequiredArgsConstructor
public class FeignClientRequestInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String reqId = MDC.get(Constants.REQ_ID_KEY);
            if (StringUtils.isNotBlank(reqId)) {
                requestTemplate.header(Constants.REQ_ID_KEY, reqId);
            }

            String authToken = MDC.get(Constants.TOKEN_HEADER);
            if (StringUtils.isNotBlank(authToken)) {
                requestTemplate.header(Constants.TOKEN_HEADER, authToken);
            }
        };
    }
}
