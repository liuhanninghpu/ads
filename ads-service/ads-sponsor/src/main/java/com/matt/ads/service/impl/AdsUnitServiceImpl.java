package com.matt.ads.service.impl;

import com.matt.ads.constant.CommonStatus;
import com.matt.ads.constant.Constants;
import com.matt.ads.dao.AdsPlanRepository;
import com.matt.ads.dao.AdsUnitRepository;
import com.matt.ads.entity.AdsPlan;
import com.matt.ads.entity.AdsUnit;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IAdsUnitService;
import com.matt.ads.vo.AdsUnitGetRequest;
import com.matt.ads.vo.AdsUnitRequest;
import com.matt.ads.vo.AdsUnitResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
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
    @Transactional
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
    @Transactional
    public AdsUnitResponse UpdateUnit(AdsUnitRequest request) throws AdsException {
        if(!request.updateValidate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }
        //修改广告单元不能跨广告计划
        AdsUnit AdsUnit = unitRepository.findbyId(request.getId());
        if(null == AdsUnit){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_UNIT_NOT_FOUND);
        }

        if(request.getBudget() != null){
            AdsUnit.setBudget(request.getBudget());
        }

        if(request.getPositionType() != null){
            AdsUnit.setPositionType(request.getPositionType());
        }

        if(request.getUnitName() != null){
            AdsUnit.setUnitName(request.getUnitName());
        }

        if(request.getUnitStatus() != null){
            AdsUnit.setUnitStatus(request.getUnitStatus());
        }

        AdsUnit = unitRepository.save(AdsUnit);

        return new AdsUnitResponse(AdsUnit.getId(),AdsUnit.getUnitName());
    }

    @Override
    @Transactional
    public void deleteUnit(AdsUnitRequest request) throws AdsException {
        if(!request.deleteValidate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }

        AdsUnit AdsUnit = unitRepository.findbyId(request.getId());
        if(null == AdsUnit){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_UNIT_NOT_FOUND);
        }

        AdsUnit.setIsDelete(CommonStatus.VALID.getStatus());
        AdsUnit.setUnitStatus(CommonStatus.INVALID.getStatus());
        unitRepository.save(AdsUnit);
    }

    @Override
    @Transactional
    public List<AdsUnit> getUnit(AdsUnitGetRequest request) throws AdsException {
        if(!request.validate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }
        return unitRepository.findAllByIdInAndUserId(
               request.getIds(),request.getUserId()
        );
    }

}
