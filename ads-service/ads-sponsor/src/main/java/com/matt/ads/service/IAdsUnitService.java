package com.matt.ads.service;

import com.matt.ads.entity.AdsUnit;
import com.matt.ads.exception.AdsException;
import com.matt.ads.vo.AdsUnitGetRequest;
import com.matt.ads.vo.AdsUnitRequest;
import com.matt.ads.vo.AdsUnitResponse;

import java.util.List;

public interface IAdsUnitService {
    AdsUnitResponse createUnit(AdsUnitRequest request) throws AdsException;


    AdsUnitResponse UpdateUnit(AdsUnitRequest request) throws AdsException;


    void deleteUnit(AdsUnitRequest request) throws AdsException;


    List<AdsUnit> getUnit(AdsUnitGetRequest request) throws AdsException;
}
