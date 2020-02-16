package com.matt.ads.service;


import com.matt.ads.entity.AdsPlan;
import com.matt.ads.exception.AdsException;
import com.matt.ads.vo.AdsPlanGetRequest;
import com.matt.ads.vo.AdsPlanRequest;
import com.matt.ads.vo.AdsPlanResponse;

import java.util.List;

public interface IAdsPlanService {

    /*
    创建推广计划
     */
    AdsPlanResponse createAdsPlan(AdsPlanRequest request) throws AdsException;

    /*
    获取推广计划
     */
    List<AdsPlan> getAdPlanByIds(AdsPlanGetRequest request) throws AdsException;

    /*
    更新推广计划
     */
    AdsPlanResponse updateAdsPlan(AdsPlanRequest request) throws AdsException;

    /*
    删除推广计划
     */
    void deleteAdsPlan(AdsPlanRequest request) throws AdsException;
}
