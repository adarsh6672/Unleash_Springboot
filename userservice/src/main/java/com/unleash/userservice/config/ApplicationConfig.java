package com.unleash.userservice.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
