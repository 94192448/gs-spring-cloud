package com.example;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-27
 */

@Configuration
public class ApolloOpenApiClientConfig {

    String portalUrl = "http://172.16.20.31:8070"; // portal url
    String token = "90aa03a8d34f687c7a93fe0d97d3e1a2a60e602a"; // 申请的token

    @Bean
    public ApolloOpenApiClient apolloOpenApiClient(){

        ApolloOpenApiClient client = ApolloOpenApiClient.newBuilder()
                .withPortalUrl(portalUrl)
                .withToken(token)
                .build();

        return client;
    }

}
