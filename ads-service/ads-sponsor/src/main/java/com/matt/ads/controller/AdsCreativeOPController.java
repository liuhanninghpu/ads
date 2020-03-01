package com.matt.ads.controller;


import com.alibaba.fastjson.JSON;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IAdsCreativeService;
import com.matt.ads.vo.AdsCreativeRequest;
import com.matt.ads.vo.AdsCreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AdsCreativeOPController {
    private final IAdsCreativeService creativeService;

    @Autowired
    public AdsCreativeOPController(IAdsCreativeService creativeService){
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public AdsCreativeResponse createCreative(
            @RequestBody AdsCreativeRequest request
    ) throws AdsException{
         log.info("ad-sponsor: createCreative -> {}",
                 JSON.toJSON(request));
         return creativeService.createCreative(request);
    }

}
