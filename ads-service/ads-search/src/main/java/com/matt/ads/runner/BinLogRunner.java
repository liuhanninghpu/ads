package com.matt.ads.runner;

import com.matt.ads.mysql.BinLogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BinLogRunner implements CommandLineRunner {

    private final BinLogClient client;

    @Autowired
    public BinLogRunner (BinLogClient client){
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("coming in binlog runner...");
        client.connect();
    }
}
