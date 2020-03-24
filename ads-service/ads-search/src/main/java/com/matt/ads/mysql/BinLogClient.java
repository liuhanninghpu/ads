package com.matt.ads.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.matt.ads.mysql.listener.AggregatioonListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class BinLogClient {
    private BinaryLogClient client;

    private final BinLogConfig config;
    private final AggregatioonListener listener;

    @Autowired
    public BinLogClient(BinLogConfig config,AggregatioonListener listener){
        this.config = config;
        this.listener = listener;
    }

    public void connect(){
        new Thread(()->{
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );

            if(!StringUtils.isEmpty(config.getBinlogName()) &&
            !config.getPosition().equals(-1)){
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }

            client.registerEventListener(listener);

            try {
                log.info("connect to mysql start");
                client.connect();
                log.info("connect to mysql done");
            } catch (IOException ex) {
                log.error(ex.getMessage());
                ex.printStackTrace();
            }

        }).start();
    }

    public void close(){
        try {
                client.disconnect();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
