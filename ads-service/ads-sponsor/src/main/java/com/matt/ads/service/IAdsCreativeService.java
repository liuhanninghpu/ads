package com.matt.ads.service;


import com.matt.ads.vo.AdsCreativeRequest;
import com.matt.ads.vo.AdsCreativeResponse;

public interface IAdsCreativeService {

    AdsCreativeResponse createCreative(AdsCreativeRequest request);
}
