package com.sv.oauthservice.security.event;

import com.sv.oauthservice.service.IUserService;
import com.sv.usercommonsservice.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationErrorSuccessHandler implements AuthenticationEventPublisher {

    private final IUserService userService;

    public AuthenticationErrorSuccessHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        if(authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String message = "Success Login: " + user.getUsername();
        log.info(message);

        User userFound = userService.findByUserName(authentication.getName());

//        if(user.getIntentos() != null && user.getIntentos() > 0) {
//            user.setIntentos(0);
//            usuarioService.update(user, user.getId());
//        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {
        String message = "Login error: " + e.getMessage();
        log.error(message);
    }
}
