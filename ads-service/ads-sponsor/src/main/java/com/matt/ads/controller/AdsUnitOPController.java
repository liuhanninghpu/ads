package com.matt.ads.controller;


import com.alibaba.fastjson.JSON;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IAdsUnitService;
import com.matt.ads.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AdsUnitOPController {
    private final IAdsUnitService adsUnitService;

    @Autowired
    public AdsUnitOPController(IAdsUnitService adsUnitService){
        this.adsUnitService = adsUnitService;
    }


    @PostMapping("/create/adsUnit")
    public AdsUnitResponse createUnit(@RequestBody AdsUnitRequest request) throws AdsException{
        log.info("ad-sponsor: createUnit -> {}", JSON.toJSONString(request));
        return adsUnitService.createUnit(request);
    }

    @PostMapping("/create/AdsUnitKeyword")
    public AdsUnitKeywordResponse createUnitKeyword(
            @RequestBody AdsUnitKeywordRequest request
            )throws AdsException{
        log.info("ad-sponsor : createAdsUnitKeyword -> {}",
                JSON.toJSONString(request));
        return adsUnitService.createUnitKeyword(request);
    }

    @PostMapping("/create/AdsUnitIt")
    public AdsUnitItResponse createUnitIt(
            @RequestBody AdsUnitItRequest request
    ) throws AdsException{
        log.info("ad-sponsor : createAdsUnitIt -> {}",
                JSON.toJSONString(request));
        return adsUnitService.createUnitIt(request);
    }


    @PostMapping("/create/AdsUnitDistrict")
    public AdsUnitDistrictResponse createAdsUnitDistrict(
            @RequestBody AdsUnitDistrictRequest request
    ) throws  AdsException{
        log.info("ad-sponsor : createAdsUnitDistrict -> {}",
                JSON.toJSONString(request));
        return adsUnitService.createUnitDistrict(request);
    }


    @PostMapping("/create/creativeUnit")
    public AdsCreativeUnitResponse createCreativeUnit(
            @RequestBody AdsCreativeUnitRequest request
    ) throws AdsException {
        log.info("ad-sponsor: createCreativeUnit -> {}",
                JSON.toJSONString(request));
        return adsUnitService.createcreativeUnit(request);
    }



}
