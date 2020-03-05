package com.matt.ads.client;

import com.matt.ads.client.vo.AdsPlan;
import com.matt.ads.client.vo.AdsPlanGetRequest;
import com.matt.ads.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

//断路器
@Component
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdsPlan>> getAdsPlans(AdsPlanGetRequest request) {
        return new CommonResponse<>(-1,"eurake-client-ads-sponsor error");
    }
}
