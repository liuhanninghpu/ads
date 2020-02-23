package com.matt.ads.vo;


import com.matt.ads.entity.unit_condition.AdsUnitDistrict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsUnitDistrictRequest {

    private List<AdsUnitDistrict> unitDistricts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitDistrict{
        private Long unitId;
        private String itTag;
    }
}
