package com.matt.ads.service.impl;

import com.matt.ads.dao.AdsCreativeRepository;
import com.matt.ads.entity.AdsCreative;
import com.matt.ads.service.ICreativeService;
import com.matt.ads.vo.AdsCreativeRequest;
import com.matt.ads.vo.AdsCreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsCreativeServiceImpl implements ICreativeService {
    private AdsCreativeRepository creativeRepository;

    @Autowired
    public AdsCreativeServiceImpl(AdsCreativeRepository creativeRepository){
        this.creativeRepository = creativeRepository;
    }

    @Override
    public AdsCreativeResponse createCreative(AdsCreativeRequest request) {
        AdsCreative creative = creativeRepository.save(
            request.convertToEntity()
        );
        return new AdsCreativeResponse(creative.getId(),creative.getName());
    }
}
