package com.sv.oauthservice.service;

import com.sv.oauthservice.client.UserFeignClient;
import com.sv.usercommonsservice.domain.User;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUserService, UserDetailsService {

    private final UserFeignClient userFeignClient;

    public UserService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userFeignClient.findByUserName(username);

            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

            log.info("Authenticated user: " + username);

            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), user.getEnabled(), true, true, true,
                    authorities);

        } catch (FeignException e) {
            String error = "Login error, user not found: '" + username;
            log.error(error);

            throw new UsernameNotFoundException(error);
        }
    }

    @Override
    public User findByUserName(String userName) {
        return userFeignClient.findByUserName(userName);
    }

    @Override
    public User update(User user, Long id) {
        return null;
    }
}
