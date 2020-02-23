package com.matt.ads.service;

import com.matt.ads.entity.AdsUnit;
import com.matt.ads.entity.unit_condition.AdsUnitDistrict;
import com.matt.ads.exception.AdsException;
import com.matt.ads.vo.*;

import java.util.List;

public interface IAdsUnitService {
    AdsUnitResponse createUnit(AdsUnitRequest request) throws AdsException;


    AdsUnitResponse UpdateUnit(AdsUnitRequest request) throws AdsException;


    void deleteUnit(AdsUnitRequest request) throws AdsException;


    List<AdsUnit> getUnit(AdsUnitGetRequest request) throws AdsException;

    AdsUnitKeywordResponse createUnitKeyword(AdsUnitKeywordRequest request) throws AdsException;

    AdsUnitItResponse createUnitIt(AdsUnitItRequest request) throws AdsException;

    AdsUnitDistrictResponse createUnitDistrict(AdsUnitDistrictRequest request) throws AdsException;
}
