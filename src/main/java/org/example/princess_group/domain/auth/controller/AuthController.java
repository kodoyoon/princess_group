package org.example.princess_group.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.auth.dto.AuthRequestDto;
import org.example.princess_group.domain.auth.dto.AuthResponseDto;
import org.example.princess_group.domain.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auths")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public AuthResponseDto createAuth(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AuthRequestDto requestDto) {
        return AuthService.createAuth(userDetails.getUser(), requestDto);
    }

    @GetMapping("/{userId}")
    public AuthResponseDto getAuth(@PathVariable Long UserId) {
        return AuthService.getAuth(userId);
    }

    @PutMapping("/{id}")
    public Long updateAuth(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody AuthRequestDto requestDto) {
        return AuthService.updateAuth(userDetails.getUser(), id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAuth(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        AuthService.deleteAuth(userDetails.getUser(), id);
    }


}
