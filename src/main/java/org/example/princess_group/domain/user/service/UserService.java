package org.example.princess_group.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.domain.user.entity.User;

public interface UserService {

    boolean isValidUserId(Long userId);
    void createUser(CreateUserRequest request);
    void loginUser(CreateUserRequest request, HttpServletRequest req );

    void clearSession(HttpServletRequest req);
}
