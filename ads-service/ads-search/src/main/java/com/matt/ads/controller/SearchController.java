package com.matt.ads.controller;


import com.alibaba.fastjson.JSON;
import com.matt.ads.annotation.IgnoreResponseAdvice;
import com.matt.ads.client.SponsorClient;
import com.matt.ads.client.vo.AdsPlan;
import com.matt.ads.client.vo.AdsPlanGetRequest;
import com.matt.ads.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.ExcludeSuperclassListeners;
import javax.persistence.MappedSuperclass;
import java.util.List;

@Slf4j
@RestController
public class SearchController {
    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    private static String COMMON_RESPONS_URL  = "http://eureka-client-ads-sponsor/ads-sponsor/get/adsPlan";

    @Autowired
    public SearchController(RestTemplate restTemplate,SponsorClient sponsorClient){
        this.sponsorClient = sponsorClient;
        this.restTemplate = restTemplate;
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdsPlans")
    public CommonResponse<List<AdsPlan>> getAdsPlans(
            @RequestBody AdsPlanGetRequest request
    ){
        log.info("ads-search: getAdsPlans -> {}",
                JSON.toJSONString(request));
        return sponsorClient.getAdsPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdsPlansByRibbon")
    public CommonResponse<List<AdsPlan>> getAdsPlanByRebbon(
            @RequestBody AdsPlanGetRequest request
            ){
        log.info("ad-search : getAdsPlanByRibbon -> {}",
                JSON.toJSONString(request));
        return restTemplate.postForEntity(
            COMMON_RESPONS_URL,
                request,
                CommonResponse.class
        ).getBody();
    }

}
