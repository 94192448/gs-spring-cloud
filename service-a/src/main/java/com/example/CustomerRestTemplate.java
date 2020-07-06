package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-06
 */
@Slf4j
public class CustomerRestTemplate extends RestTemplate {

    @Autowired
    RestRoutesProperties restRoutesProperties;
    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        try {
            String[] urls = url.getPath().split("/");

            String backendHost = restRoutesProperties.getRoutes().get(url.getHost()+urls[1]);
            if (backendHost!=null){
                url = new URI(url.toString().replace(url.getHost()+"/"+urls[1],backendHost));
            }
        }catch (URISyntaxException e){
            log.error("Rewrite url error ",e.getMessage());
        }


        return super.doExecute(url, method, requestCallback, responseExtractor);
    }
}
