package com.matt.ads.service.impl;

import com.matt.ads.commonUtils.CommonUtils;
import com.matt.ads.constant.Constants;
import com.matt.ads.dao.AdsUserRepository;
import com.matt.ads.entity.AdsUser;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IUserService;
import com.matt.ads.vo.CreateUserRequest;
import com.matt.ads.vo.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class UserServiceImpl implements IUserService {

    private final AdsUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdsUserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*
     创建用户,开启事物
     */
    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdsException {
        if(!request.validate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }
        //同名用户
        AdsUser oldUser = userRepository.findByUsername(request.getUsername());

        if(oldUser != null){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_SAME_NAME_ERROR);
        }

        AdsUser newUser = userRepository.save(new AdsUser(
                request.getUsername(),
                CommonUtils.md5(request.getUsername())
                ));

        return new CreateUserResponse(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getToken(),
                newUser.getCreateTime(),
                newUser.getUpdateTime()
        );
    }
}
