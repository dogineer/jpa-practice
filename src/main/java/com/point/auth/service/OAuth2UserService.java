package com.point.auth.service;

import com.point.auth.userinfo.KakaoUserInfo;
import com.point.auth.userinfo.OAuth2UserInfo;
import com.point.auth.userinfo.User;
import com.point.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if (provider.equals("kakao")) {
            log.info("카카오 로그인 요청");
            log.info("getAttributes : {}", oAuth2User.getAttributes());

            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        assert oAuth2UserInfo != null;
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String nickname = oAuth2UserInfo.getName();

        User joinUser = User.builder()
            .id(UUID.randomUUID())
            .providerId(providerId)
            .email(email)
            .loginId(loginId)
            .name(nickname)
            .joinAt(LocalDateTime.now())
            .build();

        userService.addUser(joinUser, providerId);

        return oAuth2User;
    }
}
