package com.connehealth.transfer;

import com.connehealth.entities.PracticeFile;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by zhuyu on 2015/8/10.
 */
public class PracticeFileTransfer {
    private Long id;
    private Date createOn;
    private Long createBy;
    private Long modifyBy;
    private Date modifyOn;
    private int status;

    private String fileName;
    private String options;
    private String fileData;

    public PracticeFileTransfer(){

    }

    public PracticeFileTransfer(Long id,
            Date createOn,
            Long createBy,
            Long modifyBy,
            Date modifyOn,
            int status,
            String fileName,
            String fileData,
            String options){
        this.id = id;
        this.createBy =createBy;
        this.createOn =createOn;
        this.modifyBy =modifyBy;
        this.modifyOn =modifyOn;
        this.status = status;
        this.fileName = fileName;
        this.fileData = fileData;
        this.options = options;
    }
    
    public PracticeFileTransfer(PracticeFile file){
        this.id = file.getId();
        this.createBy = file.getCreateBy();
        this.createOn = file.getCreateOn();
        this.modifyBy = file.getModifyBy();
        this.modifyOn = file.getModifyOn();
        this.status = file.getStatus();
        this.fileName = file.getFileName();
        this.options = file.getOptions();

        try {
            this.fileData = new String(file.getFileData(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public PracticeFile toPracticeFile(){
        PracticeFile file = new PracticeFile();

        file.setId(this.id);
        file.setCreateBy(this.createBy);
        file.setCreateOn(this.createOn);
        file.setModifyBy(this.modifyBy);
        file.setModifyOn(this.modifyOn);
        file.setStatus(this.status);
        file.setFileName(this.fileName);
        file.setOptions(this.options);

        try {
            file.setFileData(this.fileData.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyOn() {
        return modifyOn;
    }

    public void setModifyOn(Date modifyOn) {
        this.modifyOn = modifyOn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
