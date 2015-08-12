package com.connehealth.entities;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by zhuyu on 2015/8/10.
 */
@XmlRootElement
public class PracticeFile extends AuditableEntity {
    private String fileName;
    private byte[] fileData;
    private String options;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
