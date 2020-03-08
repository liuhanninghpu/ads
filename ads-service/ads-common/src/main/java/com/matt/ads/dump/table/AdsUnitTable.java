package com.matt.ads.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsUnitTable {
    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
}
