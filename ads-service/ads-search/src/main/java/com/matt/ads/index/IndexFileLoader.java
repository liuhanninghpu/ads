package com.matt.ads.index;


import com.alibaba.fastjson.JSON;
import com.matt.ads.dump.DConstant;
import com.matt.ads.dump.table.*;
import com.matt.ads.handler.AdsLevelDataHandler;
import com.matt.ads.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
@DependsOn("dataTable")
//顺序不能变化
public class IndexFileLoader {

    @PostConstruct
    public void init(){
        List<String> adsPlanStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.ADS_PLAN)
        );

        adsPlanStrings.forEach(
                p-> AdsLevelDataHandler.handleLevel2(
                        JSON.parseObject(p, AdsPlanTable.class),
                        OpType.ADD
                )
        );

        List<String> adsCreativeString = loadDumpData(
                String.format(
                        DConstant.DATA_ROOT_DIR,
                        DConstant.ADS_CREATIVE
                )
        );
        adsCreativeString.forEach(c -> AdsLevelDataHandler.handleLevel2(
                JSON.parseObject(c, AdsCreativeTable.class),
                OpType.ADD
        ));

        List<String> adsUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.ADS_UNIT)
        );
        adsUnitStrings.forEach(u-> AdsLevelDataHandler.handleLevel3(
                JSON.parseObject(u, AdsUnitTable.class),
                OpType.ADD
        ));

        List<String> adsCreativeUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.ADS_UNIT)
        );
        adsCreativeUnitStrings.forEach(cu-> AdsLevelDataHandler.handleLevel3(
                JSON.parseObject(cu, AdsCreativeUnitTable.class),
                OpType.ADD
        ));

        List<String> adsUnitItStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.ADS_UNIT_IT)
        );
        adsUnitItStrings.forEach(ui-> AdsLevelDataHandler.handleLevel4(
                JSON.parseObject(ui, AdsUnitItTable.class),
                OpType.ADD
        ));


        List<String> adsUnitDistrictStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.ADS_UNIT_DISTRICT)
        );
        adsUnitDistrictStrings.forEach(ud-> AdsLevelDataHandler.handleLevel4(
                JSON.parseObject(ud, AdsUnitDistrictTable.class),
                OpType.ADD
        ));

        List<String> adsUnitKeywordStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.ADS_UNIT_KEYWORD)
        );
        adsUnitKeywordStrings.forEach(ud-> AdsLevelDataHandler.handleLevel4(
                JSON.parseObject(ud, AdsUnitKeywordTable.class),
                OpType.ADD
        ));
    }

    private List<String> loadDumpData(String fileName){
        try(BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName)
        )){
            return br.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


}
