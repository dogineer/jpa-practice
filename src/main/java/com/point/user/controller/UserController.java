package com.point.user.controller;

import com.point.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @Transactional
    @GetMapping("/userinfo/{name}")
    public UUID userDetails(@PathVariable String name){
        return userService.findUserId(name);
    }
}
