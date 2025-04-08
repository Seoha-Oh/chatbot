package com.prjoect.chatbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${webclient.flask-base-url}")
    private String flaskBaseUrl;

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(flaskBaseUrl)
                .build();
    }
}
