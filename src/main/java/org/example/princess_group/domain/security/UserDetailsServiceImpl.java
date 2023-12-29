package org.example.princess_group.domain.security;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.user.entity.User;
import org.example.princess_group.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username).orElseThrow();
        UserDetails userDetails = new UserDetailsImpl(user);
        return userDetails;
    }
}
