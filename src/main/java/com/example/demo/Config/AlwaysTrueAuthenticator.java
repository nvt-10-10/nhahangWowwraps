package com.example.demo.Config;

import com.cksource.ckfinder.authentication.Authenticator;
import jakarta.inject.Named;

@Named
public class AlwaysTrueAuthenticator implements Authenticator {
    @Override
    public boolean authenticate() {
        return true;
    }
}
