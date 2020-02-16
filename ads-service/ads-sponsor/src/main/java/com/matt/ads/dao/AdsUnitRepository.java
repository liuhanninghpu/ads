package com.matt.ads.dao;

import com.matt.ads.entity.AdsUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsUnitRepository extends JpaRepository<AdsUnit,Long> {
        AdsUnit findByPlanIdAndUnitName(Long planId,String unitName);

        List<AdsUnit> findAllByUnitStatus(Integer unitStatus);

}
