package com.example.demo.Config;

import com.cksource.ckfinder.authentication.Authenticator;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Named
public class CustomAuthenticator implements Authenticator {
    @Inject
    private HttpServletRequest request;

    @Override
    public boolean authenticate() {
        HttpSession session = request.getSession();

        return session.getAttribute("allowedToAccessCKFinder") == Boolean.TRUE;
    }
}