package com.matt.ads.service.impl;

import com.matt.ads.commonUtils.CommonUtils;
import com.matt.ads.constant.CommonStatus;
import com.matt.ads.constant.Constants;
import com.matt.ads.dao.AdsPlanRepository;
import com.matt.ads.dao.AdsUserRepository;
import com.matt.ads.entity.AdsPlan;
import com.matt.ads.entity.AdsUser;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IAdsPlanService;
import com.matt.ads.vo.AdsPlanGetRequest;
import com.matt.ads.vo.AdsPlanRequest;
import com.matt.ads.vo.AdsPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AdsPlanServiceImpl implements IAdsPlanService {
    private final AdsUserRepository userRepository;
    private final AdsPlanRepository planRepository;

    @Autowired
    public AdsPlanServiceImpl(AdsUserRepository userRepository,
                              AdsPlanRepository planReRepository){
        this.userRepository = userRepository;
        this.planRepository = planReRepository;
    }

    @Override
    @Transactional
    public AdsPlanResponse createAdsPlan(AdsPlanRequest request) throws AdsException {
        if(!request.createValidate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }

        //确保关联User是存在的
        Optional<AdsUser> adsUser = userRepository.findById(request.getUserId());
        if(!adsUser.isPresent()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_USER_NOT_FOUND);
        }

        AdsPlan oldPlan = planRepository.findByUserIdAndPlanName(
                request.getUserId(),request.getPlanName()
        );
        if(oldPlan != null){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_SAME_PLAN_ERROR);
        }


        AdsPlan newPlan = planRepository.save(
                new AdsPlan(request.getUserId(),
                        request.getPlanName(),
                        CommonUtils.parseStringToDate(request.getStartDate()),
                        CommonUtils.parseStringToDate(request.getEndDate())
                )
        );

        return new AdsPlanResponse(
                newPlan.getId(),
                newPlan.getPlanName()
        );
    }

    @Override
    @Transactional
    public List<AdsPlan> getAdPlanByIds(AdsPlanGetRequest request) throws AdsException {
        if(!request.validate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }
        return planRepository.findAllByIdInAndUserId(
            request.getIds(),request.getUserId()
        );
    }


    @Override
    @Transactional
    public AdsPlanResponse updateAdsPlan(AdsPlanRequest request) throws AdsException {
        if(!request.updateValidate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }

        AdsPlan plan = planRepository.findByIdAndUserId(request.getId(),request.getUserId());
            if(plan == null){
                throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_PLAN_NOT_FOUND);
            }

        if(request.getStartDate() != null){
            plan.setPlanName(request.getPlanName());
        }

        if(request.getStartDate() != null){
            plan.setStartDate(CommonUtils.parseStringToDate(request.getStartDate()));
        }

        if(request.getEndDate() != null){
            plan.setEndDate(CommonUtils.parseStringToDate(request.getEndDate()));
        }

        plan.setUpdateTime(new Date());
        plan = planRepository.save(plan);
        return new AdsPlanResponse(plan.getId(),plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdsPlan(AdsPlanRequest request) throws AdsException {
        if(!request.deleteValidate()){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }

        AdsPlan plan = planRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if(plan == null){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_PLAN_NOT_FOUND);
        }

        plan.setIsDelete(CommonStatus.VALID.getStatus());
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        planRepository.save(plan);
    }
}
