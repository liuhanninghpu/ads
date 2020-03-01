package com.matt.ads.client;

import com.matt.ads.client.vo.AdsPlan;
import com.matt.ads.client.vo.AdsPlanGetRequest;
import com.matt.ads.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "eureka-client-ads-sponsor",fallback = SponsorClientHystrix.class)
public interface SponsorClient {
    @RequestMapping(value = "/ads-sponsor/get/adsPlan")
    CommonResponse<List<AdsPlan>> getAdsPlans(@RequestBody AdsPlanGetRequest request);
}
