package com.matt.ads.index.adsPlan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsPlanObject {
    private Long planId;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;

    public void update(AdsPlanObject newObject){
        if(null != newObject.getPlanId()){
            this.planId = newObject.getPlanId();
        }
        if(null != newObject.getUserId()){
            this.userId = newObject.getUserId();
        }
        if(null != newObject.getPlanStatus()){
            this.planStatus = newObject.getPlanStatus();
        }
        if(null != newObject.getStartDate()){
            this.startDate = newObject.getStartDate();
        }
        if(null != newObject.getEndDate()){
            this.endDate = newObject.getEndDate();
        }
    }
}
