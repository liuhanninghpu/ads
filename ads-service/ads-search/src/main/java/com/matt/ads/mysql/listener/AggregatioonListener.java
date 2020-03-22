package com.matt.ads.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.matt.ads.mysql.TemplateHolder;
import com.matt.ads.mysql.dto.BinlogRowData;
import com.matt.ads.mysql.dto.TableTemplate;
import com.matt.ads.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AggregatioonListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;
    private Map<String,Ilistener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregatioonListener(TemplateHolder templateHolder){
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbName,String tableName){
        return dbName+":"+tableName;
    }

    public void register(String _dbName,String _tableName,Ilistener ilistener){
        log.info("register : {}-{}",_dbName,_tableName);
        this.listenerMap.put(genKey(_dbName,_tableName),ilistener);

    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type :{}",type);
        if(type == EventType.TABLE_MAP){
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if(type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS){
            return ;
        }

        //表明和库名是否已经完成填充
        if(StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)){
            log.error("no meta data event");
        }

        //找出对应表有兴趣的监听器
        String key = genKey(this.dbName,this.tableName);
        Ilistener ilistener = this.listenerMap.get(key);
        if(null == ilistener){
            log.debug("skip {}",key);
        }
        log.info("trigger event :{}",type.name());

        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if(rowData == null){
                return ;
            }
            rowData.setEventType(type);
            ilistener.onEvent(rowData);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData){
        if(eventData instanceof WriteRowsEventData){
            return ((WriteRowsEventData) eventData).getRows();
        }

        if(eventData instanceof UpdateRowsEventData){
            return ((UpdateRowsEventData) eventData).getRows().stream().map(
                    Map.Entry::getValue
            ).collect(Collectors.toList());
        }

        if(eventData instanceof DeleteRowsEventData){
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }



    private BinlogRowData buildRowData(EventData eventData){
        TableTemplate tableTemplate = templateHolder.getTable(tableName);

        if(null == tableTemplate){
            log.warn("table {} not found",tableTemplate);
            return null;
        }

        List<Map<String,String>> afterMapList = new ArrayList<>();

        for(Serializable[] after:getAfterValues(eventData)){
            Map<String,String> afterMap = new HashMap<>();

            int colLen = after.length;

            for (int ix = 0;ix < colLen;++ix){
                //取出列名称
                String colName = tableTemplate.getPosMap().get(ix);

                //如果没有则说明不关心这个列
                if(null == colName){
                    log.debug("ignore position :{}",ix);
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName,colValue);
            }
            afterMapList.add(afterMap);
        }

        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTableTemplate(tableTemplate);
        return rowData;
    }
}
