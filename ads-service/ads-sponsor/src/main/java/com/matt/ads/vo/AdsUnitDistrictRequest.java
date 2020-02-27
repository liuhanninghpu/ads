package com.matt.ads.vo;


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
    public static class AdsUnitDistrict{
        private Long unitId;
        private String province;
        private String city;
    }
}
