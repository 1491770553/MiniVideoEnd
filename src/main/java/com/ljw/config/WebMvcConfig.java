package com.ljw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@PropertySource("classpath:application.yml")
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${upfile.fileSpace}")
    private String fileSpace;


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("file:"+fileSpace+"/");
    }


}