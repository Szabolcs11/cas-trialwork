package org.example.demo2.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.demo2.model.User;
import org.example.demo2.dto.response.BaseResponse;
import org.example.demo2.dto.response.DataResponse;
import org.example.demo2.dto.response.MessageResponse;
import org.example.demo2.repository.UserRepository;
import org.example.demo2.util.Password;

import java.util.List;

@ApplicationScoped
public class UserService {

    UserRepository userRepository = new UserRepository();

    public BaseResponse getUsers() {
        List<User> userList = userRepository.getUsers();
        return new DataResponse<>(true, userList);
    }

    public BaseResponse createUser(String email, String password) {
        if (email.isEmpty()) return new MessageResponse(false, "Email field is empty");
        if (password.isEmpty()) return new MessageResponse(false, "Password field is empty");

        String hashedPassword = Password.hashPassword(password);
        String message = userRepository.createUser(email, hashedPassword);
        if (message.equals("409")) return new MessageResponse(false, "Email already exist");
        if (message.equals("500")) return new MessageResponse(false, "Unexpected error");

        return new MessageResponse(true, "Successfully created user");
    }

    public BaseResponse getUser(String id) {
        if (id.isEmpty()) return new MessageResponse(false, "Id field is empty");

        User user = userRepository.getUserById(id);
        if (user.getId() == 0) return new MessageResponse(false, "No user found with provided id");

        return new DataResponse<>(true, user);
    }

    public BaseResponse deleteUser(String myEmail, String id) {
        if (id.isEmpty()) return new MessageResponse(false, "Id field is empty");

        User user = userRepository.getUserByEmail(myEmail);
        if (Integer.parseInt(id) == user.getId()) return new MessageResponse(false, "Can't delete yourself");

        boolean deleted = userRepository.deleteUser(id);
        if (!deleted) return new MessageResponse(false, "Unexpected error");

        return new MessageResponse(true, "User successfully deleted");
    }
}