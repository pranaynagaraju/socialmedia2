package dev.pranay.socialmedia2.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Utils {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
