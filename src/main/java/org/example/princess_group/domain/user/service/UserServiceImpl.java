package org.example.princess_group.domain.user.service;

import static org.example.princess_group.domain.user.error.UserErrorCode.CHECK_ID_PASSWORD;
import static org.example.princess_group.domain.user.error.UserErrorCode.CHECK_USER;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.domain.user.entity.User;
import org.example.princess_group.domain.user.repository.UserRepository;
import org.example.princess_group.global.exception.ServiceException;
import org.example.princess_group.global.util.StatusUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final StatusUtil statusUtil;
    public boolean isValidUserId(Long userId) {
        return false;
    }

    public void createUser(CreateUserRequest request) {
        User user = repository.findByName(request.name()).orElse(null);
        if(user == null) {
            String name = request.name();
            String password = passwordEncoder.encode(request.password());
            User signup = new User(name, password);
            repository.save(signup);
        }else{
            throw new ServiceException(CHECK_USER);
        }
    }


    public void loginUser(CreateUserRequest request, HttpServletRequest req ){
        String name = request.name();
        User user = repository.findByName(name).orElseThrow(() ->new ServiceException(CHECK_ID_PASSWORD));
        if (!passwordEncoder.matches(request.password(),user.getPassword())){
            throw  new ServiceException(CHECK_ID_PASSWORD);
        }
        statusUtil.login(request,req);
    }
}
