package com.point.user.service;

import com.point.auth.userinfo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void addUser(User joinUser, String providerId){
        log.info("로그인 요청 : {}", joinUser);

        User isProviderId = userRepository.findByProviderId(providerId);
        boolean isJoined = isProviderId != null && joinUser.getProviderId().equals(isProviderId.getProviderId());

        if (isJoined){
            log.info("이미 회원가입된 유저입니다. : {}", joinUser);
        } else {
            log.info("회원가입 완료. : {}", joinUser);
            userRepository.save(joinUser);
        }
    }

    public UUID findUserId(String name){
        return userRepository.findByName(name).getId();
    }
}
