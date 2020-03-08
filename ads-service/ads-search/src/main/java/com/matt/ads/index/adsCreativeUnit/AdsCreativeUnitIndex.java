package com.matt.ads.index.adsCreativeUnit;

import com.matt.ads.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class AdsCreativeUnitIndex implements IndexAware<String,AdsCreativeUnitObject> {

    //<adsId-unitId,creativeUnitObject>
    private static Map<String,AdsCreativeUnitObject> objectMap;

    //<adsId,unitId Set>
    private static Map<Long, Set<Long>> creativeUnitMap;

    //<unitId,adsId Set>
    private static Map<Long,Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdsCreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, AdsCreativeUnitObject value) {
        log.info("before add :{}",objectMap);
        objectMap.put(key,value);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if(CollectionUtils.isNotEmpty(unitSet)){
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(),unitSet);
        }
        unitSet.add(value.getUnitId());

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());

        if(CollectionUtils.isEmpty(creativeSet)){
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(),creativeSet);
        }
        creativeSet.add(value.getAdId());
        log.info("after add :{}",objectMap);
    }

    @Override
    public void update(String key, AdsCreativeUnitObject value) {
        log.error("creativeUnitIndex not support update");
    }

    @Override
    public void delete(String key, AdsCreativeUnitObject value) {
        log.info("before delete:{}",objectMap);
        objectMap.remove(key);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if(CollectionUtils.isNotEmpty(unitSet)){
            unitSet.remove(value.getUnitId());
        }

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if(CollectionUtils.isNotEmpty(creativeSet)){
            creativeSet.remove(value.getAdId());
        }
        log.info("after delete:{}",objectMap);
    }
}
