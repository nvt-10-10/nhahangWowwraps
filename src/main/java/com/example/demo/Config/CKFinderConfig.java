//package com.example.demo.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CKFinderConfig {
//
//    @Bean
//    public CKFinderConfiguration ckFinderConfiguration() {
//        CKFinderConfiguration config = new CKFinderConfiguration();
//        config.setBasePath("/uploads");
//        return config;
//    }
//
//    @Bean
//    public CKFinderConnector ckFinderConnector() {
//        CKFinderConnector connector = new CKFinderConnector();
//        connector.setConfiguration(ckFinderConfiguration());
//        return connector;
//    }
//
//    @Bean
//    public CKEditorConfig ckeditorConfig() {
//        CKEditorConfig config = new CKEditorConfig();
//        config.setFilebrowserBrowseUrl(ckFinderConnector().getUrl());
//        return config;
//    }
//
//}