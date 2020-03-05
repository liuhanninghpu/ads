package com.matt.ads.index.adsUnit;

import com.matt.ads.index.adsPlan.AdsPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsUnitObject {
    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;

    private AdsPlanObject adsPlanObject;

    void update(AdsUnitObject newObject){
        if(null != newObject.getUnitId()){
            this.unitId = newObject.getUnitId();
        }
        if(null != newObject.getUnitStatus()){
            this.unitStatus = newObject.getUnitStatus();
        }
        if(null != newObject.getPositionType()){
            this.positionType = newObject.positionType;
        }
        if(null != planId){
            this.planId = newObject.getPlanId();
        }
        if(null != newObject.getAdsPlanObject()){
            this.adsPlanObject = newObject.getAdsPlanObject();
        }
    }
}
