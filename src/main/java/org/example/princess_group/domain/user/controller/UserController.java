package org.example.princess_group.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.domain.user.service.UserService;
import org.example.princess_group.domain.user.service.UserServiceImpl;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody CreateUserRequest request){
        userService.createUser(request);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("회원가입에 성공했습니다.")
                .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CreateUserRequest request,
        HttpServletRequest req){
        userService.loginUser(request,req);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("로그인에 성공했습니다.")
                .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        HttpServletRequest req){
        userService.clearSession(req);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("로그아웃에 성공했습니다.")
                .build()
        );
    }
}
