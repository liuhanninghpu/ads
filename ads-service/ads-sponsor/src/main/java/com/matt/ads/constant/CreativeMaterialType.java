package com.matt.ads.constant;

import lombok.Getter;

@Getter
public enum CreativeMaterialType {
    JPG(1,"jpg"),
    BMP(2,"bmp"),
    PNG(3,"png"),

    MP4(4,"MP4"),
    AVI(5,"AVI"),

    TXT(6,"TXT");

    private int type;
    private String desc;

    CreativeMaterialType(int type,String desc){
        this.type = type;
        this.desc = desc;
    }

}
