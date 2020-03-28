package com.matt.ads.sender.index;


import com.alibaba.fastjson.JSON;
import com.matt.ads.client.vo.AdsPlan;
import com.matt.ads.dump.table.*;
import com.matt.ads.handler.AdsLevelDataHandler;
import com.matt.ads.index.DataLevel;
import com.matt.ads.index.utils.CommonUtils;
import com.matt.ads.mysql.constant.Constant;
import com.matt.ads.mysql.dto.MysqlRowData;
import com.matt.ads.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("indexsender")
public class IndexSender implements ISender {

    @Override
    public void sender(MysqlRowData rowData) {
         String level = rowData.getLevel();

         if(DataLevel.LEVEL2.getLevel().equals(level)){
             Level2RowData(rowData);
         } else if (DataLevel.LEVEL3.getLevel().equals(level)){
             Level3RowData(rowData);
         } else if (DataLevel.LEVEL4.getLevel().equals(level)){
            Level4RowData(rowData);
         } else{
            log.error("MysqlRowData ERROR:{}", JSON.toJSONString(rowData));
         }
    }

    private void Level2RowData(MysqlRowData rowData){
          if(rowData.getTableName().equals(
                  Constant.AD_PLAN_TABLE_INFO.TABLE_NAME
          )){
              List<AdsPlanTable> planTables = new ArrayList<>();

              for (Map<String,String> fieldValueMap : rowData.getFieldValueMap()){
                  AdsPlanTable planTable = new AdsPlanTable();
                  fieldValueMap.forEach((k,v) -> {
                      switch (k){
                          case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID:
                              planTable.setId(Long.valueOf(v));
                              break;
                          case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                              planTable.setId(Long.valueOf(v));
                              break;
                          case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                              planTable.setPlanStatus(Integer.valueOf(v));
                              break;
                          case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE:
                              planTable.setStartDate(
                                      CommonUtils.parseStringDate(v)
                              );
                              break;
                          case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE:
                              planTable.setEndDate(
                                      CommonUtils.parseStringDate(v)
                              );
                      }
                  });
                  planTables.add(planTable);
              }

              planTables.forEach(p->
                      AdsLevelDataHandler.handleLevel2(
                              p,rowData.getOpType()
                      ));
          }else if(rowData.getTableName().equals(
                  Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME
          )){
              List<AdsCreativeTable> creativeTables = new ArrayList<>();
              for (Map<String,String> fieldValueMap:rowData.getFieldValueMap()){
                  AdsCreativeTable creativeTable = new AdsCreativeTable();
                  fieldValueMap.forEach((k,v)->
                  {
                      switch (k){
                          case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                              creativeTable.setAdsId(Long.valueOf(v));
                              break;
                          case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                              creativeTable.setType(Integer.valueOf(v));
                              break;
                          case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                              creativeTable.setMaterialType(Integer.valueOf(v));
                              break;
                          case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                              creativeTable.setHeight(Integer.valueOf(v));
                              break;
                          case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                              creativeTable.setWidth(Integer.valueOf(v));
                              break;
                          case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                              creativeTable.setAuditStatus(Integer.valueOf(v));
                              break;
                          case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                              creativeTable.setAdsUrl(v);
                      }
                  });
                  creativeTables.add(creativeTable);
              }
              creativeTables.forEach(c->AdsLevelDataHandler.handleLevel2(c,rowData.getOpType()));
          }
    }

    private void Level3RowData(MysqlRowData rowData){
         if(rowData.getTableName().equals(Constant.AD_UNIT_TABLE_INFO.TABLE_NAME)){
             List<AdsUnitTable> unitTables = new ArrayList<>();

             for(Map<String,String> fieldValueMap : rowData.getFieldValueMap()){
                  AdsUnitTable unitTable = new AdsUnitTable();

                  fieldValueMap.forEach((k,v) -> {
                      switch (k){
                          case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                              unitTable.setUnitId(Long.valueOf(v));
                              break;
                          case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                              unitTable.setUnitStatus(Integer.valueOf(v));
                              break;
                          case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                              unitTable.setPositionType(Integer.valueOf(v));
                              break;
                          case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                              unitTable.setPlanId(Long.valueOf(v));
                              break;
                      }
                  });
                  unitTables.add(unitTable);
             }
             unitTables.forEach(u -> AdsLevelDataHandler.handleLevel3(
                     u,rowData.getOpType()
             ));
         }else if(rowData.getTableName().equals(
                 Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME
         )){
             List<AdsCreativeUnitTable> creativeUnitTables = new ArrayList<>();

             for(Map<String,String> fieldValueMap : rowData.getFieldValueMap()){

                 AdsCreativeUnitTable creativeUnitTable = new AdsCreativeUnitTable();

                 fieldValueMap.forEach((k,v) -> {
                     switch (k){
                         case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                             creativeUnitTable.setAdsId(Long.valueOf(v));
                             break;
                         case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                             creativeUnitTable.setUnitId(Long.valueOf(v));
                             break;
                     }
                 });

                 creativeUnitTables.add(creativeUnitTable);
             }

             creativeUnitTables.forEach(
                     u -> AdsLevelDataHandler.handleLevel3(u,rowData.getOpType())
             );
         }
    }

    private void Level4RowData(MysqlRowData rowData){
         switch (rowData.getTableName()){
             case Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:
                 List<AdsUnitDistrictTable> districtTables = new ArrayList<>();
                 for (Map<String,String> fieldValueMap : rowData.getFieldValueMap()){
                      AdsUnitDistrictTable districtTable = new AdsUnitDistrictTable();

                      fieldValueMap.forEach((k,v) -> {
                          switch (k){
                              case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                  districtTable.setUnitId(Long.valueOf(v));
                                  break;
                              case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                  districtTable.setProvince(v);
                                  break;
                              case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                  districtTable.setCity(v);
                                  break;
                          }
                      });
                      districtTables.add(districtTable);
                 }

                 districtTables.forEach(d->
                         AdsLevelDataHandler.handleLevel4(d,rowData.getOpType()));
                 break;
             case Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME:
                 List<AdsUnitItTable> itTables = new ArrayList<>();
                 for(Map<String,String> fieldValueMap : rowData.getFieldValueMap()){
                     AdsUnitItTable itTable = new AdsUnitItTable();

                     fieldValueMap.forEach((k,v)->{
                         switch (k){
                             case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                                 itTable.setUnitId(Long.valueOf(v));
                                 break;
                             case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                                 itTable.setItTag(v);
                                 break;
                         }
                     });
                     itTables.add(itTable);
                 }
                 itTables.forEach(i->AdsLevelDataHandler.handleLevel4(i,rowData.getOpType()));
                 break;
             case Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME:
                 List<AdsUnitKeywordTable> keywordTables = new ArrayList<>();
                 for(Map<String,String> fieldValueMap : rowData.getFieldValueMap()){
                    AdsUnitKeywordTable keywordTable = new AdsUnitKeywordTable();

                    fieldValueMap.forEach((k,v)->{
                        switch (k){
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                keywordTable.setKeyword(v);
                                break;
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                keywordTable.setUnitId(Long.valueOf(v));
                                break;
                        }
                    });
                    keywordTables.add(keywordTable);
                 }
                 keywordTables.forEach(k->AdsLevelDataHandler.handleLevel4(k,rowData.getOpType()));
         }
    }
}
