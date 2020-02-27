package com.matt.ads.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsUnitKeywordRequest {

    private List<AdsUnitKeyword> unitKeywords;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdsUnitKeyword{
        private Long unitId;
        private String keyword;
    }
}
