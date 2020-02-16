package com.matt.ads.dao;

import com.matt.ads.entity.AdsPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsPlanRepository extends JpaRepository<AdsPlan,Long> {

    AdsPlan findByIdAndUserId(Long id,Long userId);

    List<AdsPlan> findAllByIdInAndUserId(List<Long> ids,Long userId);

    List<AdsPlan> findAllByPlanStatus(Integer status);

    AdsPlan findByUserIdAndPlanName(Long userId,String planName);
}
