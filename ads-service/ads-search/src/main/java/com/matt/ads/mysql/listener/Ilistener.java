package com.matt.ads.mysql.listener;


import com.matt.ads.mysql.dto.BinlogRowData;

public interface Ilistener {

    void register();

    void onEvent(BinlogRowData eventData);
}
