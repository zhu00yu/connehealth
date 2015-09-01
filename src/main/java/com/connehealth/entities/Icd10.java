package com.connehealth.entities;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class Icd10 {
    private Long id;
    private String icdNo;
    private String additionalNo;
    private String mnemonics;
    private String name;
    private String englishName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcdNo() {
        return icdNo;
    }

    public void setIcdNo(String icdNo) {
        this.icdNo = icdNo;
    }

    public String getAdditionalNo() {
        return additionalNo;
    }

    public void setAdditionalNo(String additionalNo) {
        this.additionalNo = additionalNo;
    }

    public String getMnemonics() {
        return mnemonics;
    }

    public void setMnemonics(String mnemonics) {
        this.mnemonics = mnemonics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}
