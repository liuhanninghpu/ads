package com.matt.ads.controller;

import com.alibaba.fastjson.JSON;
import com.matt.ads.entity.AdsPlan;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IAdsPlanService;
import com.matt.ads.service.impl.AdsPlanServiceImpl;
import com.matt.ads.vo.AdsPlanGetRequest;
import com.matt.ads.vo.AdsPlanRequest;
import com.matt.ads.vo.AdsPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class AdsPlanOPController {
    private final IAdsPlanService adsPlanService;

    @Autowired
    public AdsPlanOPController(IAdsPlanService adsPlanService){
        this.adsPlanService = adsPlanService;
    }


    @PostMapping("create/adsPlan")
    public AdsPlanResponse createAdsPlan(
            @RequestBody AdsPlanRequest request
            ) throws AdsException{
         log.info("ad-sponsor: createAdsPlan -> {}", JSON.toJSONString(request));
         return adsPlanService.createAdsPlan(request);
    }

    @PostMapping("/get/adsPlan")
    public List<AdsPlan> getAdsPlanByIds(
            @RequestBody AdsPlanGetRequest request
            )
            throws AdsException{
        log.info("ad-sponsor: getAdsPlanByIds -> {}", JSON.toJSONString(request));
        return adsPlanService.getAdPlanByIds(request);
    }

    @PutMapping("/uodate/adsPlan")
    public AdsPlanResponse updateAdsPlan(
            @RequestBody AdsPlanRequest request
    )throws AdsException{
        log.info("ad-sponsor: updateAdsPlan -> {}", JSON.toJSONString(request));
        return adsPlanService.updateAdsPlan(request);
    }

    @DeleteMapping("/delete/adsPlan")
    public void deleteAdsPlan(
            @RequestBody AdsPlanRequest request
    )throws AdsException{
        log.info("ad-sponsor: deleteAdsPlan -> {}", JSON.toJSONString(request));
        adsPlanService.deleteAdsPlan(request);
    }

}
