package com.matt.ads.sender;

import com.matt.ads.mysql.dto.MysqlRowData;

public interface ISender {

    void sender(MysqlRowData rowData);


}
