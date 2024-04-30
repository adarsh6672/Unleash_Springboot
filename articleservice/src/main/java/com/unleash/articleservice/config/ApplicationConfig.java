package com.unleash.articleservice.config;

import com.cloudinary.Cloudinary;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Value("${cloudinary.cloud_name}")
    private String cloudname;

    @Value("${cloudinary.api_key}")
    private String apikey;

    @Value("${cloudinary.api_secret}")
    private String secret;




    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap();
        config.put("cloud_name",cloudname);
        config.put("api_key",apikey);
        config.put("api_secret" ,secret);
        config.put("secure", true);

        return new Cloudinary(config);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(10)); // Maximum file size
        factory.setMaxRequestSize(DataSize.ofMegabytes(10)); // Maximum request size
        return factory.createMultipartConfig();
    }
}
