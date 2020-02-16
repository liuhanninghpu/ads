package com.matt.ads.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ads_creative")
public class AdsCreative {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id",nullable = false)
     private Long id;

     @Basic
     @Column(name = "name",nullable = false)
     private String name;

     @Basic
     @Column(name = "type",nullable = false)
     private Integer type;

     /*
     物料类型
      */
     @Basic
     @Column(name = "material_type",nullable = false)
     private Integer material_type;

    @Basic
    @Column(name = "height",nullable = false)
    private Integer height;

    @Basic
    @Column(name = "width",nullable = false)
    private Integer width;

    /*
    物料大小
     */
    @Basic
    @Column(name = "size",nullable = false)
    private Long size;

    /*
    持续时长，视频用
     */
     @Basic
    @Column(name = "duration",nullable = false)
    private Integer duration;

    /*
     审核状态
     */
    @Basic
    @Column(name = "audit_status",nullable = false)
    private Integer auditStatus;

    @Basic
    @Column(name = "user_id",nullable = false)
    private Integer userId;

    @Basic
    @Column(name = "url",nullable = false)
    private String  url;

    @Basic
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

    public AdsCreative(String name,
                       Integer type,
                       Integer material_type,
                       Integer height,
                       Integer width,
                       Long size,
                       Integer duration,
                       Integer userId,
                       String url
    ){
        this.name = name;
        this.type = type;
        this.material_type = material_type;
        this.height = height;
        this.width = width;
        this.size = size;
        this.duration = duration;
        this.userId = userId;
        this.url = url;
    }
}
