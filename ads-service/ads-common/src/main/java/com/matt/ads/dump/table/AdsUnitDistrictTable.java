package com.matt.ads.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsUnitDistrictTable {
    private Long unitId;
    private String province;
    private String city;
}
