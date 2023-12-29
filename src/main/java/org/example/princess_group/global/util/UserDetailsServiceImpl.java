package org.example.princess_group.global.util;

import static org.example.princess_group.domain.user.error.UserErrorCode.CHECK_ID_PASSWORD;

import org.example.princess_group.domain.user.entity.User;
import org.example.princess_group.domain.user.repository.UserRepository;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() ->new ServiceException(CHECK_ID_PASSWORD));

        return new UserDetailsImpl(user);
    }
}