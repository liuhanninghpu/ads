package com.matt.ads.index.adsPlan;

import com.matt.ads.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AdsPlanIndex implements IndexAware<Long,AdsPlanObject> {
    private static Map<Long,AdsPlanObject> objectMap;

    static {
        //线程安全
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdsPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdsPlanObject value) {
        log.info("before add:{}",objectMap);
        objectMap.put(key,value);
        log.info("after add:{}s",objectMap);
    }

    @Override
    public void update(Long key, AdsPlanObject value) {
        log.info("before update:{}",objectMap);

        AdsPlanObject oldObejct = objectMap.get(key);

        if(null == oldObejct){
            objectMap.put(key,value);
        }else{
            oldObejct.update(value);
        }
        log.info("after update:{}",objectMap);
    }

    @Override
    public void delete(Long key, AdsPlanObject value) {
        log.info("before delete:{}",objectMap);
        objectMap.remove(key);
        log.info("after delete:{}",objectMap);
    }
}
