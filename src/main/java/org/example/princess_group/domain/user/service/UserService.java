package org.example.princess_group.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.domain.user.dto.CreateUserResponse;
import org.example.princess_group.domain.user.entity.User;
import org.example.princess_group.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserResponse signUp(CreateUserRequest request) throws Exception {
        User user = new User(request.userId(), passwordEncoder.encode(request.password()));
        if (userRepository.findByUserId(request.userId()).isPresent()) {
            throw new Exception("같은 아이디의 회원이 존재합니다.");
        }
        System.out.println("===================================");
        System.out.println(user.getUserId() + "\n" + user.getPassword());
        userRepository.save(user);
        return new CreateUserResponse(user.getUserId());
    }
}
