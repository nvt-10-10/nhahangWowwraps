package com.example.demo.ckfinder.authentication;


import com.cksource.ckfinder.authentication.Authenticator;
import com.example.demo.ckfinder.config.CustomConfig;
import org.springframework.context.ApplicationContext;

import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
public class ConfigBasedAuthenticator implements Authenticator {
    @Inject
    private ApplicationContext applicationContext;

    @Override
    public boolean authenticate() {
        CustomConfig config = applicationContext.getBean(CustomConfig.class);
        return config.isEnabled();
    }
}