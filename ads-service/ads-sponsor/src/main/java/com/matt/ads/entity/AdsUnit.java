package com.matt.ads.entity;
import com.matt.ads.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ads_unit")
public class AdsUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Basic
    @Column(name ="plan_id",nullable = false)
    private Long planId;

    @Basic
    @Column(name = "unit_name",nullable = false)
    private String unitName;

    @Basic
    @Column(name = "unit_status",nullable = false)
    private Integer unitStatus;

    /*
      广告位类型，开屏 插屏 信息流 。。。
     */
    @Basic
    @Column(name = "position_type",nullable = false)
    private Integer positionType;

    @Basic
    @Column(name = "budget",nullable = false)
    private Long budget;

    @Basic
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    public AdsUnit(Long planId,String unitName,Integer positionType,Long budget){
        this.planId = planId;
        this.unitName = unitName;
        this.unitStatus = CommonStatus.VALID.getStatus();
        this.positionType = positionType;
        this.budget = budget;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
