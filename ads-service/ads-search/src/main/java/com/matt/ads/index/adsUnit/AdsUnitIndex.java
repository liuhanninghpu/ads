package com.matt.ads.index.adsUnit;

import com.matt.ads.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AdsUnitIndex implements IndexAware<Long, AdsUnitObject> {

    private static Map<Long,AdsUnitObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }
    @Override
    public AdsUnitObject get(Long key) {
        return null;
    }

    @Override
    public void add(Long key, AdsUnitObject value) {
        log.info("before add :{}",objectMap);
        objectMap.put(key,value);
        log.info("after add :{}",objectMap);
    }

    @Override
    public void update(Long key, AdsUnitObject value) {
        log.info("before update:{}",objectMap);
        AdsUnitObject oldObject = objectMap.get(key);
        if(null == oldObject){
            objectMap.put(key,value);
        }else{
            oldObject.update(value);
        }
        log.info("after update:{}",objectMap);
    }

    @Override
    public void delete(Long key, AdsUnitObject value) {
        log.info("before delete :{}",objectMap);
        objectMap.remove(key);
        log.info("after delete :{}",objectMap);
    }
}
