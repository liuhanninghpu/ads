package com.matt.ads.sender.index;


import com.alibaba.fastjson.JSON;
import com.matt.ads.client.vo.AdsPlan;
import com.matt.ads.dump.table.AdsPlanTable;
import com.matt.ads.handler.AdsLevelDataHandler;
import com.matt.ads.index.DataLevel;
import com.matt.ads.index.utils.CommonUtils;
import com.matt.ads.mysql.constant.Constant;
import com.matt.ads.mysql.dto.MysqlRowData;
import com.matt.ads.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
          }
    }

    private void Level3RowData(MysqlRowData rowData){

    }

    private void Level4RowData(MysqlRowData rowData){

    }
}
