package org.example.princess_group.global.util;

import static org.example.princess_group.domain.user.error.UserErrorCode.NOT_LOGIN;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.domain.user.repository.UserRepository;
import org.example.princess_group.domain.user.service.UserService;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusUtil {

    private final String LOGIN_USER = "login_user";
    private final UserDetailsServiceImpl service;


    public CreateUserRequest getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ServiceException(NOT_LOGIN);
        }
        return (CreateUserRequest) session.getAttribute(LOGIN_USER);
    }


    public void login(CreateUserRequest data, HttpServletRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) service.loadUserByUsername(data.name());
        HttpSession session = request.getSession(true);
        session.setAttribute(LOGIN_USER, userDetails.getUser());
    }

    public void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(0);
    }

    public boolean isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null;
    }
}