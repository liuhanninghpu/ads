package com.matt.ads.mysql.dto;

import com.matt.ads.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {
    private String tableName;
    private String Level;

    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    private Map<Integer,String> posMap = new HashMap<>();
}
