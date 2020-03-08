package com.matt.ads.index.adsCreative;


import com.matt.ads.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AdsCreativeIndex implements IndexAware<Long, AdsCreativeObject> {

    private static Map<Long, AdsCreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdsCreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdsCreativeObject value) {
        log.info("before add :{}",objectMap);
        objectMap.put(key,value);
        log.info("after add :{}",objectMap);
    }

    @Override
    public void update(Long key, AdsCreativeObject value) {
        log.info("before update :{}",objectMap);
        AdsCreativeObject oldObject = objectMap.get(key);
        if(null == oldObject){
            objectMap.put(key,value);
        }else{
            oldObject.update(value);
        }
        log.info("after update :{}",objectMap);
    }

    @Override
    public void delete(Long key, AdsCreativeObject value) {
        log.info("before delete :{}",objectMap);
        objectMap.remove(key);
        log.info("before delete :{}",objectMap);
    }
}
