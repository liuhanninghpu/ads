package com.matt.ads.controller;

import com.alibaba.fastjson.JSON;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IUserService;
import com.matt.ads.vo.CreateUserRequest;
import com.matt.ads.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserOPController {

    private final IUserService userService;

    @Autowired
    public UserOPController(IUserService userService){
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(
            @RequestBody CreateUserRequest request
            )throws AdsException{
        log.info("ad-sponsor:createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }


}
