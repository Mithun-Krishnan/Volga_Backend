package com.example.volgaProject.auth.security;

import com.example.volgaProject.user.entity.UserEntity;
import com.example.volgaProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=userRepository.loadUserByName(username).orElseThrow(()->
                new UsernameNotFoundException("Invalid user name"));

        return new SecurityUser(user);

    }
}
