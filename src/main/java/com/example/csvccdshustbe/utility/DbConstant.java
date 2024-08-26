package com.example.csvccdshustbe.utility;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class DbConstant {


    @Autowired
    private Environment env;

    @PostConstruct
    public void setUpConfigData() {
//        URL = env.getProperty("hust.ktx.static.location");
//        STATIC_CONTEXT = env.getProperty("hust.ktx.static.context");
//        DOMAIN_FILE = env.getProperty("vn.compedia.static.file.avatar_path");
    }

}
