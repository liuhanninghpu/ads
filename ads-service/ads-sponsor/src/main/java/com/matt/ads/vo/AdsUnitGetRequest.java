package com.matt.ads.vo;

import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
public class AdsUnitGetRequest {
    private Long userId;
    private List<Long> ids;


    public boolean validate(){
        return userId != null
                && !CollectionUtils.isEmpty(ids);
    }
}
