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
@Table(name = "ads_plan")
public class AdsPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Basic
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Basic
    @Column(name = "plan_name",nullable = false)
    private String planName;

    @Basic
    @Column(name = "plan_status",nullable = false)
    private Integer planStatus;

    @Basic
    @Column(name = "is_delete",nullable = false)
    private Integer isDelete;

    @Basic
    @Column(name = "start_date",nullable = false)
    private Date startDate;

    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Basic
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time",nullable = false)
    private  Date updateTime;

    public AdsPlan(Long userId,String planName,Date startDate,Date endDate){
        this.userId = userId;
        this.planName = planName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.planStatus = CommonStatus.VALID.getStatus();
        this.isDelete =  CommonStatus.INVALID.getStatus();
    }

}
