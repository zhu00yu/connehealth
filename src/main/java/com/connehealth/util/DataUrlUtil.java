package com.connehealth.util;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhuyu on 2015/8/11.
 */
public class DataUrlUtil {
    public static byte[] convertToImage(String dataUrl){
        byte[] fileData = null;
        String encodingPrefix = "base64,";
        int contentStartIndex = dataUrl.indexOf(encodingPrefix);
        if(contentStartIndex != -1){
            contentStartIndex += encodingPrefix.length();
            try {
                fileData = Base64.decode(dataUrl.substring(contentStartIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return fileData;
    }

    public static byte[] convertToImage(byte[] dbData){
        byte[] fileData = null;
        try {
            String dataUrl = new String(dbData, "UTF-8");
            fileData = convertToImage(dataUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fileData == null){
            fileData = dbData;
        }
        return fileData;
    }
}
