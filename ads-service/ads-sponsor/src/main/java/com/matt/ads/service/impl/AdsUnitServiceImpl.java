package com.matt.ads.service.impl;

import com.matt.ads.constant.CommonStatus;
import com.matt.ads.constant.Constants;
import com.matt.ads.dao.AdsCreativeRepository;
import com.matt.ads.dao.AdsPlanRepository;
import com.matt.ads.dao.AdsUnitRepository;
import com.matt.ads.dao.unit_condition.AdsCreativeUnitRepository;
import com.matt.ads.dao.unit_condition.AdsUnitDistrictRepository;
import com.matt.ads.dao.unit_condition.AdsUnitItRepository;
import com.matt.ads.dao.unit_condition.AdsUnitKeywordRepository;
import com.matt.ads.entity.AdsPlan;
import com.matt.ads.entity.AdsUnit;
import com.matt.ads.entity.unit_condition.AdsCreativeUnit;
import com.matt.ads.entity.unit_condition.AdsUnitDistrict;
import com.matt.ads.entity.unit_condition.AdsUnitIt;
import com.matt.ads.entity.unit_condition.AdsUnitKeyword;
import com.matt.ads.exception.AdsException;
import com.matt.ads.service.IAdsUnitService;
import com.matt.ads.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


public class AdsUnitServiceImpl implements IAdsUnitService {

    private final AdsUnitRepository unitRepository;
    private final AdsPlanRepository planRepository;
    private final AdsUnitKeywordRepository unitKeywordRepository;
    private final AdsUnitItRepository unitItRepository;
    private final AdsUnitDistrictRepository unitDistrictRepository;
    private final AdsCreativeRepository creativeRepository;
    private final AdsCreativeUnitRepository creativeUnitRepository;

    @Autowired
    public AdsUnitServiceImpl(AdsUnitRepository unitRepository,
                              AdsPlanRepository planReRepository,
                              AdsUnitKeywordRepository unitKeywordRepository,
                              AdsUnitItRepository unitItRepository,
                              AdsUnitDistrictRepository unitDistrictRepository,
                              AdsCreativeRepository creativeRepository,
                              AdsCreativeUnitRepository creativeUnitRepository){
        this.unitRepository = unitRepository;
        this.planRepository = planReRepository;
        this.unitItRepository = unitItRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
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

    @Override
    @Transactional
    public AdsUnitKeywordResponse createUnitKeyword(AdsUnitKeywordRequest request) throws AdsException {
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdsUnitKeywordRequest.AdsUnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if(!isRelatedUnitExist(unitIds)){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        List<AdsUnitKeyword> unitKeywords = new ArrayList<>();
        if(!CollectionUtils.isEmpty(request.getUnitKeywords())){
            request.getUnitKeywords().forEach(i-> unitKeywords.add(
                    new AdsUnitKeyword(i.getUnitId(),i.getKeyword())
            ));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream()
                    .map(AdsUnitKeyword::getId).collect(Collectors.toList());
        }

        return new AdsUnitKeywordResponse(ids);
    }

    @Override
    @Transactional
    public AdsUnitItResponse createUnitIt(AdsUnitItRequest request) throws AdsException {
        List<Long> unitIds = request.getUnitIts().stream().
                map(AdsUnitItRequest.AdsUnitIt::getUnitId).
                collect(Collectors.toList());

        if(!isRelatedUnitExist(unitIds)){
              throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }
        List<AdsUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new AdsUnitIt(i.getUnitId(), i.getItTag())
        ));
        List<Long> ids = unitItRepository.saveAll(unitIts).stream().map(
                AdsUnitIt::getId
        ).collect(Collectors.toList());

        return new AdsUnitItResponse(ids);
    }

    @Override
    @Transactional
    public AdsUnitDistrictResponse createUnitDistrict(AdsUnitDistrictRequest request) throws AdsException{
        List<Long> unitIds = request.getUnitDistricts().stream().
                map(AdsUnitDistrictRequest.AdsUnitDistrict::getUnitId).
                collect(Collectors.toList());

        if(!isRelatedUnitExist(unitIds)){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }
        List<AdsUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(d->unitDistricts.add(
                new AdsUnitDistrict(d.getUnitId(),d.getProvince(),d.getCity())
        ));
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts).stream().map(
                AdsUnitDistrict::getId
        ).collect(Collectors.toList());
        return new AdsUnitDistrictResponse(ids);
    }

    @Override
    @Transactional
    public AdsCreativeUnitResponse creativeUnit(AdsCreativeUnitRequest request) throws AdsException {
        List<Long> unitIds = request.getUnitItems().stream().map(
            AdsCreativeUnitRequest.CreativeUnitItem::getUnitId
        ).collect(Collectors.toList());

        List<Long> creativeIds = request.getUnitItems().stream().map(
                AdsCreativeUnitRequest.CreativeUnitItem::getCreativeId
        ).collect(Collectors.toList());

        if(!(isRelatedUnitExist(unitIds) && isRelatedUnitExist(creativeIds))){
            throw new AdsException(Constants.ErrMsg.ADS_SPONSOR_QEQUEST_PARAM_ERROR);
        }

        List<AdsCreativeUnit> creativeUnits = new ArrayList<>();
        request.getUnitItems().forEach(i -> creativeUnits.add(
                new AdsCreativeUnit(i.getCreativeId(),i.getUnitId())
        ));

        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits)
                .stream()
                .map(AdsCreativeUnit::getId)
                .collect(Collectors.toList());

        return new AdsCreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds){
        if(CollectionUtils.isEmpty(unitIds)){
            return false;
        }
        return unitRepository.findAllById(unitIds).size() ==
                new HashSet<>(unitIds).size();
    }

    private boolean isRelateCreativeExist(List<Long> creativeIds){
        if(CollectionUtils.isEmpty(creativeIds)){
            return false;
        }

        return creativeRepository.findAllById(creativeIds).size() ==
                new HashSet<>(creativeIds).size();
    }

}
