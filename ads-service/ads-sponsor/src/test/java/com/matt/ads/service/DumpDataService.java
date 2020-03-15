package com.matt.ads.service;

import com.alibaba.fastjson.JSON;
import com.matt.ads.Application;
import com.matt.ads.constant.CommonStatus;
import com.matt.ads.dao.AdsCreativeRepository;
import com.matt.ads.dao.AdsPlanRepository;
import com.matt.ads.dao.AdsUnitRepository;
import com.matt.ads.dao.unit_condition.AdsCreativeUnitRepository;
import com.matt.ads.dao.unit_condition.AdsUnitDistrictRepository;
import com.matt.ads.dao.unit_condition.AdsUnitItRepository;
import com.matt.ads.dao.unit_condition.AdsUnitKeywordRepository;
import com.matt.ads.dump.table.*;
import com.matt.ads.entity.AdsCreative;
import com.matt.ads.entity.AdsPlan;
import com.matt.ads.entity.AdsUnit;
import com.matt.ads.entity.unit_condition.AdsCreativeUnit;
import com.matt.ads.entity.unit_condition.AdsUnitDistrict;
import com.matt.ads.entity.unit_condition.AdsUnitIt;
import com.matt.ads.entity.unit_condition.AdsUnitKeyword;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {
    @Autowired
    private AdsPlanRepository planRepository;
    @Autowired
    private AdsUnitRepository unitRepository;
    @Autowired
    private AdsCreativeRepository creativeRepository;
    @Autowired
    private AdsCreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdsUnitDistrictRepository unitDistrictRepository;
    @Autowired
    private AdsUnitKeywordRepository unitKeywordRepository;
    @Autowired
    private AdsUnitItRepository unitItRepository;

    private void dumpAdsPlanTable(String fileName){
        List<AdsPlan> adsPlan = planRepository.findAllByPlanStatus(
                CommonStatus.VALID.getStatus()
        );
        if(CollectionUtils.isEmpty(adsPlan)){
            return;
        }
        List<AdsPlanTable> planTables = new ArrayList<>();
        adsPlan.forEach(p -> planTables.add(
                new AdsPlanTable(
                        p.getId(),
                        p.getUserId(),
                        p.getPlanStatus(),
                        p.getStartDate(),
                        p.getEndDate()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdsPlanTable planTable:planTables){
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdsPlanTable error");
        }
    }

    private void dumpAdsUnitTable(String fileName){
        List<AdsUnit> adsUnits = unitRepository.findAllByUnitStatus(
                CommonStatus.VALID.getStatus()
        );
        if(CollectionUtils.isEmpty(adsUnits)){
            return;
        }
        List<AdsUnitTable> unitTables = new ArrayList<>();
        adsUnits.forEach(p -> unitTables.add(
                new AdsUnitTable(
                        p.getId(),
                        p.getUnitStatus(),
                        p.getPositionType(),
                        p.getPlanId()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdsUnitTable unitTable:unitTables){
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdsUnitTable error");
        }
    }


    private void dumpAdsCreativeTable(String fileName){
        List<AdsCreative> creatives = creativeRepository.findAll();
        if(CollectionUtils.isEmpty(creatives)){
            return;
        }
        List<AdsCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(p -> creativeTables.add(
                new AdsCreativeTable(
                       p.getId(),
                       p.getName(),
                       p.getType(),
                       p.getMaterialType(),
                       p.getAuditStatus(),
                       p.getWidth(),
                       p.getHeight(),
                       p.getUrl()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdsCreativeTable creativeTable:creativeTables){
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdsCreativeTable error");
        }
    }

    private void dumpAdsCreativeUnitTable(String fileName){
        List<AdsCreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if(CollectionUtils.isEmpty(creativeUnits)){
            return;
        }
        List<AdsCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(p -> creativeUnitTables.add(
                new AdsCreativeUnitTable(
                        p.getUnitId(),
                        p.getCreativeId()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdsCreativeUnitTable creativeUnitTable:creativeUnitTables){
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpCreativeUnitTable error");
        }
    }


    private void dumpAdsUnitDistrictTable(String fileName){
        List<AdsUnitDistrict> unitDistricts = unitDistrictRepository.findAll();
        if(CollectionUtils.isEmpty(unitDistricts)){
            return;
        }
        List<AdsUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(p -> unitDistrictTables.add(
                new AdsUnitDistrictTable(
                    p.getUnitId(),
                    p.getCity(),
                    p.getProvince()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdsUnitDistrictTable unitDistrictTable:unitDistrictTables){
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdsUnitDistrictTable error");
        }
    }

    private void dumpAdsUnitKeywordTable(String fileName){
        List<AdsUnitKeyword> unitKeywords = unitKeywordRepository.findAll();
        if(CollectionUtils.isEmpty(unitKeywords)){
            return;
        }
        List<AdsUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(p -> unitKeywordTables.add(
                new AdsUnitKeywordTable(
                        p.getUnitId(),
                        p.getKeyword()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdsUnitKeywordTable unitKeywordTable:unitKeywordTables){
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdsUnitKeywordTable error");
        }
    }

    private void dumpAdsUnitItTable(String fileName){
        List<AdsUnitIt> unitIts = unitItRepository.findAll();
        if(CollectionUtils.isEmpty(unitIts)){
            return;
        }
        List<AdsUnitItTable> unitItTables = new ArrayList<>();
        unitItTables.forEach(p -> unitItTables.add(
                new AdsUnitItTable(
                      p.getUnitId(),
                      p.getItTag()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdsUnitItTable unitItTable:unitItTables){
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdsUnitItTable error");
        }
    }
}
