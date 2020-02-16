package com.matt.ads.service.impl;

import com.matt.ads.constant.Constants;
import com.matt.ads.dao.AdsPlanRepository;
import com.matt.ads.dao.AdsUnitRepository;
import com.matt.ads.dao.AdsUserRepository;
import com.matt.ads.entity.AdsPlan;
import com.matt.ads.entity.AdsUnit;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IAdsUnitService;
import com.matt.ads.vo.AdsUnitRequest;
import com.matt.ads.vo.AdsUnitResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AdsUnitServiceImpl implements IAdsUnitService {

    private final AdsUnitRepository unitRepository;
    private final AdsPlanRepository planRepository;

    @Autowired
    public AdsUnitServiceImpl(AdsUnitRepository unitRepository,
                              AdsPlanRepository planReRepository){
        this.unitRepository = unitRepository;
        this.planRepository = planReRepository;
    }

    @Override
    public AdsUnitResponse createUnit(AdsUnitRequest request) throws AdsException {
        if(!request.createValidate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }

        Optional<AdsPlan> adsPlan = planRepository.findById(request.getPlanId());
        if(!adsPlan.isPresent()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_PLAN_NOT_FOUND);
        }

        AdsUnit oldAdsUnit = unitRepository.findByPlanIdAndUnitName(
                request.getPlanId(),request.getUnitName()
        );

        if(oldAdsUnit != null){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_SAME_UNIT_ERROR);
        }

        AdsUnit newAdsUnit = unitRepository.save(
                new AdsUnit(request.getPlanId(),
                        request.getUnitName(),
                        request.getPositionType(),
                        request.getBudget()
        ));

        return new AdsUnitResponse(newAdsUnit.getId(),newAdsUnit.getUnitName());
    }

    @Override
    public AdsUnitResponse UpdateUnit(AdsUnitRequest request) throws AdsException {
        return null;
    }

    @Override
    public AdsUnitResponse deleteUnit(AdsUnitRequest request) throws AdsException {
        return null;
    }

    @Override
    public AdsUnitResponse getUnit(AdsUnitRequest request) throws AdsException {
        return null;
    }
}
