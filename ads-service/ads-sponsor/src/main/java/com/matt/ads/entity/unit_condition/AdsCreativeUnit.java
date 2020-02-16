package com.matt.ads.entity.unit_condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ads_creative_unit")
public class AdsCreativeUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id",nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "creative_id",nullable = false)
    private Long creativeId;

    public AdsCreativeUnit(Long unitId,Long creativeId){
        this.unitId = unitId;
        this.creativeId = creativeId;
    }
}
