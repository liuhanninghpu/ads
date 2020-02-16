package com.matt.ads.commonUtils;

import com.matt.ads.exception.AdsException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

    public class CommonUtils {
        private static String[] parsePatterns = {
                "yyyy-MM-dd","yyyy/MM/dd","yyyy.MM.dd"
        };

        public static String md5(String value){
            return DigestUtils.md5Hex(value).toUpperCase();
        }

        public static Date parseStringToDate(String dateString) throws AdsException{
            try {
                return DateUtils.parseDate(dateString,parsePatterns);
            }catch (Exception ex){
                throw new AdsException(ex.getMessage());
            }
        }
}
