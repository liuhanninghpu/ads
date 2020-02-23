package com.matt.ads.vo;


import com.matt.ads.entity.unit_condition.AdsUnitKeyword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsUnitKeywordResponse {
    private List<Long> ids;
}
