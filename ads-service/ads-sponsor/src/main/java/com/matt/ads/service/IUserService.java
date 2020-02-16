package com.matt.ads.service;

import com.matt.ads.exception.AdsException;
import com.matt.ads.vo.CreateUserRequest;
import com.matt.ads.vo.CreateUserResponse;

public interface IUserService {
    /*
     *创建用户
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdsException;
}
