package com.matt.ads.service;

import com.matt.ads.exception.AdsException;
import com.matt.ads.vo.AdsUnitRequest;
import com.matt.ads.vo.AdsUnitResponse;

public interface IAdsUnitService {
    AdsUnitResponse createUnit(AdsUnitRequest request) throws AdsException;


    AdsUnitResponse UpdateUnit(AdsUnitRequest request) throws AdsException;


    AdsUnitResponse deleteUnit(AdsUnitRequest request) throws AdsException;


    AdsUnitResponse getUnit(AdsUnitRequest request) throws AdsException;
}
