package org.example.princess_group.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.domain.user.dto.CreateUserResponse;
import org.example.princess_group.domain.user.service.UserService;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public RootResponse signUp(@RequestBody CreateUserRequest request) {
        CreateUserResponse response;
        try {
            response = userService.signUp(request);
            return new RootResponse(HttpStatus.CREATED.toString(), "회원가입 성공", response);
        } catch (Exception e) {
            return new RootResponse(HttpStatus.BAD_REQUEST.toString(), "회원가입 실패", e.getMessage());
        }
    }
}
