package org.example.demo2.dto.response;

import java.util.Date;

public class LoginResponse {
    private final String token;
    private final Date lastLogin;

    public LoginResponse(String token, Date lastLogin) {
        this.token = token;
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public Date getLastLogin() {
        return lastLogin;
    }
}
