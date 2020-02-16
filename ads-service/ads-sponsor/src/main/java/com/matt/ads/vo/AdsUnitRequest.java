package com.matt.ads.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsUnitRequest {
    private Long planId;
    private String unitName;

    private Integer positionType;
    private Long budget;

    public boolean createValidate(){
        return null != planId
                && !StringUtils.isEmpty(unitName)
                && positionType != null
                && budget != null;
    }

    public boolean updateValidate(){
        return null != planId;
    }


    public boolean deleteValidate(){
        return null != planId;
    }

}
