package org.example.princess_group.domain.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface{

    @Override
    public boolean isValidUserId(Long userId) {
        return false;
    }
}
