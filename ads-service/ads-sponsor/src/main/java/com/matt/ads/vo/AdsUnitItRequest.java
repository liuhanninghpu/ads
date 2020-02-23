package com.matt.ads.vo;


import com.matt.ads.entity.unit_condition.AdsUnitIt;
import com.matt.ads.entity.unit_condition.AdsUnitKeyword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsUnitItRequest {

    private List<AdsUnitIt> unitIts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class UnitIt{
        private Long unitId;
        private String itTag;
    }
}
