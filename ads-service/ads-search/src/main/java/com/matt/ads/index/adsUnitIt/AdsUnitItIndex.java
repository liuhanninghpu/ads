package com.matt.ads.index.adsUnitIt;

import com.matt.ads.index.IndexAware;
import com.matt.ads.index.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
public class AdsUnitItIndex implements IndexAware<String, Set<Long>> {

    //<itTag,adsUnitId set>
    private static Map<String,Set<Long>> itUnitMap;

    //<unitId,itTag set>
    private static Map<Long,Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex,before add :{}",unitItMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
            key,itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);
        for(Long unitId:value){
            Set<String> its = CommonUtils.getorCreate(
                    unitId,unitItMap,
                    ConcurrentSkipListSet::new
            );
            its.add(key);
        }
        log.info("UnitItIndex,after add :{}",unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("unitItIdex,before delete:{}",unitItMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key,itUnitMap,
                CopyOnWriteArraySet::new
        );
        unitIds.removeAll(value);

        for(Long unitId:value){
            Set<String> itTagSet = CommonUtils.getorCreate(
                    unitId,unitItMap,
                    ConcurrentSkipListSet::new
            );
            itTagSet.remove(key);
        }

        log.info("unitItIdex,after delete:{}",unitItMap);
    }

    public boolean match(Long unitId, List<String> itTags){
        if(unitItMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItMap.get(unitId))){
            Set<String> unitKeyWords = unitItMap.get(unitId);
            return CollectionUtils.isSubCollection(itTags,unitKeyWords);
        }
        return false;
    }

}
