package com.matt.ads.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsCreativeTable {
        private Long adsId;
        private String name;
        private Integer type;
        private Integer materialType;
        private Integer width;
        private Integer height;
        private Integer auditStatus;
        private String adsUrl;
}
