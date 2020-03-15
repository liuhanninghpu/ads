package com.matt.ads.handler;

import com.alibaba.fastjson.JSON;
import com.matt.ads.dump.table.*;
import com.matt.ads.index.DataTable;
import com.matt.ads.index.IndexAware;
import com.matt.ads.index.adsCreative.AdsCreativeIndex;
import com.matt.ads.index.adsCreative.AdsCreativeObject;
import com.matt.ads.index.adsCreativeUnit.AdsCreativeUnitIndex;
import com.matt.ads.index.adsCreativeUnit.AdsCreativeUnitObject;
import com.matt.ads.index.adsPlan.AdsPlanIndex;
import com.matt.ads.index.adsPlan.AdsPlanObject;
import com.matt.ads.index.adsUnit.AdsUnitIndex;
import com.matt.ads.index.adsUnit.AdsUnitObject;
import com.matt.ads.index.adsUnitDistrict.AdsUnitDistrictIndex;
import com.matt.ads.index.adsUnitIt.AdsUnitItIndex;
import com.matt.ads.index.utils.CommonUtils;
import com.matt.ads.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
   1.索引之间存在层级的划分,也就是依赖关系的划分
   2.加载全量索引其实是增量索引“添加”是一种特殊实现
 */
@Slf4j
public class AdsLevelDataHandler {

    public static void handleLevel2(AdsPlanTable planTable,OpType type){
        AdsPlanObject planObject = new AdsPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handleBinlogEvent(
                DataTable.of(AdsPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type
        );
    }

    public static void handleLevel2(AdsCreativeTable creativeTable,
                                    OpType type){
        AdsCreativeObject creativeObject = new AdsCreativeObject(
                creativeTable.getAdsId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdsUrl()
        );
        handleBinlogEvent(
                DataTable.of(AdsCreativeIndex.class),
                creativeObject.getAdsId(),
                creativeObject,
                type
        );
    }

    public static void handleLevel3(AdsUnitTable unitTable,OpType type){
        AdsPlanObject planObject = DataTable.of(
                AdsPlanIndex.class
        ).get(unitTable.getPlanId());
        if(null == planObject){
            log.error("handleLevel3 found AdsPlanObject error:{}",unitTable.getPlanId());
            return ;
        }

        AdsUnitObject unitObject = new AdsUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                planObject
        );

        handleBinlogEvent(
                DataTable.of(AdsUnitIndex.class),
                unitTable.getUnitId(),
                unitObject,
                type
        );
    }

    public static void handleLevel3(AdsCreativeUnitTable creativeUnitTable,OpType type){
        if(type == OpType.UPDATE){
            log.error("CreativeUnitIndex not support update");
            return;
        }

        AdsUnitObject unitObject = DataTable.of(
                AdsUnitIndex.class
        ).get(creativeUnitTable.getUnitId());

        AdsCreativeObject creativeObject = DataTable.of(
                AdsCreativeIndex.class
        ).get(creativeUnitTable.getAdsId());
        if(null == unitObject || null == creativeObject){
            log.error("AdCreativeUnitTbale index error:{}", JSON.toJSON(creativeUnitTable));
            return ;
        }
        AdsCreativeUnitObject creativeUnitObject = new AdsCreativeUnitObject(
            creativeUnitTable.getAdsId(),
            creativeUnitTable.getUnitId()
        );
        handleBinlogEvent(
                DataTable.of(AdsCreativeUnitIndex.class),
                CommonUtils.stringConcat(
                        creativeUnitObject.getAdId().toString(),
                        creativeUnitObject.getUnitId().toString()
                ),
                creativeUnitObject,
                type
        );
    }

    public static void handleLevel4(AdsUnitDistrictTable unitDistrictTable,OpType type){
        if(type ==OpType.UPDATE){
            log.error("district index can not support update");
        }

        AdsUnitObject unitObject = DataTable.of(
                AdsUnitIndex.class
        ).get(unitDistrictTable.getUnitId());
        if(unitObject == null){
            log.error("AdUnitDistrictTable index error:{}",
                    unitDistrictTable.getUnitId());
        }

        String key = CommonUtils.stringConcat(
                unitDistrictTable.getProvince(),
                unitDistrictTable.getCity()
        );

        Set<Long> value =  new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );
        handleBinlogEvent(
                DataTable.of(AdsUnitDistrictIndex.class),
                key,
                value,
                type
        );
    }

    public static void handleLevel4(AdsUnitItTable unitItTable,OpType type){
        if(type == OpType.UPDATE){
            log.error("it index can not support update");
            return ;
        }

        AdsUnitObject unitObject = DataTable.of(
                AdsUnitIndex.class
        ).get(unitItTable.getUnitId());
        if(unitObject == null){
            log.error("AdsUnitItTable index error:{}",unitItTable.getUnitId());
            return ;
        }

        Set<Long> value = new HashSet<>(
            Collections.singleton(unitItTable.getUnitId())
        );

        handleBinlogEvent(
                DataTable.of(AdsUnitItIndex.class),
                unitItTable.getItTag(),
                value,
                type
        );
    }


    public static void handleLevel4(AdsUnitKeywordTable keywordTable,OpType type){
        if(type == OpType.UPDATE){
            log.error("it index can not support update");
            return ;
        }

        AdsUnitObject unitObject = DataTable.of(
                AdsUnitIndex.class
        ).get(keywordTable.getUnitId());
        if(unitObject == null){
            log.error("AdsUnitKeywordTable index error:{}",keywordTable.getUnitId());
            return ;
        }

        Set<Long> value = new HashSet<>(
                Collections.singleton(keywordTable.getUnitId())
        );

        handleBinlogEvent(
                DataTable.of(AdsUnitItIndex.class),
                keywordTable.getKeyword(),
                value,
                type
        );
    }

    private static  <K,V> void handleBinlogEvent(IndexAware<K,V> index,
                                                 K key,
                                                 V value,
                                                 OpType type){
            switch (type){
                case ADD:
                    index.add(key,value);
                    break;
                case UPDATE:
                    index.update(key,value);
                    break;
                case DELETE:
                    index.delete(key,value);
                    break;
            }
    }
}
