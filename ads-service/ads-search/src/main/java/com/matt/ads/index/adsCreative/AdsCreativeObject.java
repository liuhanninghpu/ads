package com.matt.ads.index.adsCreative;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsCreativeObject {
    private Long adsId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String adsUrl;

    public void update(AdsCreativeObject newObject){
            if(null != newObject.getAdsId()){
                this.adsId = newObject.getAdsId();
            }

            if(null != newObject.getName()){
                this.name = newObject.getName();
            }

            if(null != newObject.getType()){
                this.type= newObject.getType();
            }


            if(null != newObject.getMaterialType()){
                this.materialType = newObject.getMaterialType();
            }

            if(null != newObject.getHeight()){
                this.height = newObject.getWidth();
            }

            if(null != newObject.getWidth()){
                this.width = newObject.getWidth();
            }

            if(null != newObject.getAuditStatus()){
                this.auditStatus = newObject.getAuditStatus();
            }

            if(null != newObject.getAdsUrl()){
                this.adsUrl = newObject.getAdsUrl();
            }
    }
}
