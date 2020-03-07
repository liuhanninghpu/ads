package com.matt.ads.index.adsUnitDistrict;

import com.matt.ads.index.IndexAware;
import com.matt.ads.index.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;
import sun.plugin.com.event.COMEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class AdsUnitDistrictIndex implements IndexAware<String, Set<Long>> {

    private static Map<String,Set<Long>> districtUnitMap;
    private static Map<Long,Set<String>> unitDistrictMap;


    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }


    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDIstrictIndex,before add :{}",unitDistrictMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
            key,districtUnitMap,
                ConcurrentSkipListSet::new
        );

        unitIds.addAll(value);

        for (Long unitId:value){
            Set<String> districts = CommonUtils.getorCreate(
                    unitId,
                    unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districts.add(key);
        }

        log.info("UnitDIstrictIndex,after add :{}",unitDistrictMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.info("district index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDIstrictIndex,before delete :{}",unitDistrictMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key,
                districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId:value){
            Set<String> districts = CommonUtils.getorCreate(
                    unitId,
                    unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districts.remove(key);
        }

        log.info("UnitDIstrictIndex,before delete :{}",unitDistrictMap);
    }
}
