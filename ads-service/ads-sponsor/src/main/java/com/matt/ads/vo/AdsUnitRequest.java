package com.matt.ads.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsUnitRequest {
    private Long id;
    private Long planId;
    private String unitName;

    private Integer positionType;
    private Long budget;
    private Integer unitStatus;

    public boolean createValidate(){
        return null != planId
                && !StringUtils.isEmpty(unitName)
                && positionType != null
                && budget != null;
    }

    public boolean updateValidate(){
        return null != planId
                && id != null;
    }


    public boolean deleteValidate(){
        return null != id;
    }


    public boolean validate(){
        return null != id;
    }
}
