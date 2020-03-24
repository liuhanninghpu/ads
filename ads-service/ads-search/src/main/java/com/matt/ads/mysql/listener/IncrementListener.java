package com.matt.ads.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.matt.ads.mysql.constant.Constant;
import com.matt.ads.mysql.constant.OpType;
import com.matt.ads.mysql.dto.BinlogRowData;
import com.matt.ads.mysql.dto.MysqlRowData;
import com.matt.ads.mysql.dto.TableTemplate;
import com.matt.ads.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource(name="")
    private ISender sender;

    private AggregatioonListener aggregatioonListener;

    @Autowired
    public IncrementListener(AggregatioonListener aggregatioonListener){
        this.aggregatioonListener = aggregatioonListener;
    }

    @Override
    public void register(){
        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k,v)->
                aggregatioonListener.register(v,k,this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate tableTemplate = eventData.getTableTemplate();
        EventType eventType = eventData.getEventType();
        //包装成最后需要投递的数据
        MysqlRowData rowData = new MysqlRowData();
        rowData.setTableName(eventData.getTableTemplate().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        //取出模板中该操作对应的字段
        List<String> fieldList = tableTemplate.getOpTypeFieldSetMap().get(opType);
        if(null == fieldList){
            log.warn("{} not support for {}",opType,tableTemplate.getTableName());
            return ;
        }

        for (Map<String,String> afterMap : eventData.getAfter()){
            Map<String,String> _afterMap = new HashMap<>();
            for(Map.Entry<String,String> entry : afterMap.entrySet()){
                String colName = entry.getKey();
                String colValue = entry.getValue();
                _afterMap.put(colName,colValue);
            }
        }

        sender.sender(rowData);
    }


}
