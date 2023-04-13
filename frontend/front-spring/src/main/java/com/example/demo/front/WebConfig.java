package com.example.demo.front;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.FixedContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.strategies(
                List.of(new FixedContentNegotiationStrategy(MediaType.APPLICATION_JSON)));
    }
}

// https://k11i.biz/blog/2020/03/25/the-easiest-way-to-return-error-response-in-json/
