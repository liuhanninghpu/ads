package com.matt.ads.entity.unit_condition;
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
@Table(name = "ads_unit_district")
public class AdsUnitDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id",nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "province",nullable = false)
    private String province;

    @Basic
    @Column(name = "city",nullable = false)
    private String city;

    public AdsUnitDistrict(Long unitId,String province,String city){
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }
}
