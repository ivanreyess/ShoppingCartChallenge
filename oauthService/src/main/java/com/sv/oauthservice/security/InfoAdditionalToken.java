package com.sv.oauthservice.security;

import com.sv.oauthservice.service.IUserService;
import com.sv.usercommonsservice.domain.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAdditionalToken implements TokenEnhancer {

    private final IUserService userService;

    public InfoAdditionalToken(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new HashMap<>();
        User user = userService.findByUserName(authentication.getName());
        info.put("name", user.getName());
        info.put("lastName", user.getLastName());
        info.put("email", user.getEmail());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
