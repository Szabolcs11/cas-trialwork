package org.example.demo2.service;

import org.example.demo2.dto.response.BaseResponse;
import org.example.demo2.dto.response.DataResponse;
import org.example.demo2.dto.response.LoginResponse;
import org.example.demo2.dto.response.MessageResponse;
import org.example.demo2.repository.AuthRepository;
import org.example.demo2.repository.UserRepository;
import org.example.demo2.util.Password;

import java.util.Date;
import java.util.Objects;

public class AuthService {
    AuthRepository authRepository = new AuthRepository();
    UserRepository userRepository = new UserRepository();

    public BaseResponse loginUser(String email, String password) {
        if (email.isEmpty()) return new MessageResponse(false, "Email field is empty");
        if (password.isEmpty()) return new MessageResponse(false, "Password field is empty");

        String token = authRepository.loginUser(email, password);
        if (token.isEmpty()) return new MessageResponse(false, "Password incorrect");

        Date lastLogin = authRepository.getLastLogin(email);

        boolean updated = authRepository.updateLastLogin(email);
        if (!updated) return new MessageResponse(false, "Unexpected error");

        LoginResponse loginResponse = new LoginResponse(token, lastLogin);

        return new DataResponse<>(true, loginResponse);
    }

    public BaseResponse changePassword(String email, String password, String passwordAgain) {
        if (!Objects.equals(password, passwordAgain)) return new MessageResponse(false, "Passwords are not equal");
        if (password.length() < 6) return new MessageResponse(false, "Password minimum length is 6");

        String hashedPassword = Password.hashPassword(password);
        boolean passwordChanged = authRepository.changePassword(email, hashedPassword);
        if (!passwordChanged) return new MessageResponse(true, "Error during password change");

        return new MessageResponse(true, "Password successfully changed");
    }

    public BaseResponse getUser(String email) {
        if (email.isEmpty()) return new MessageResponse(false, "Unexpected error");
        return new DataResponse<>(true, userRepository.getUserByEmail(email));
    }
}
