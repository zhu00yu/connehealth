package com.connehealth.entities;

import java.util.Date;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class Vaccine extends AuditableEntity {
    private String code;
    private String fullName;
    private String shortName;
    private String mnemonics1;
    private String mnemonics2;
    private String mnemonics3;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMnemonics1() {
        return mnemonics1;
    }

    public void setMnemonics1(String mnemonics1) {
        this.mnemonics1 = mnemonics1;
    }

    public String getMnemonics2() {
        return mnemonics2;
    }

    public void setMnemonics2(String mnemonics2) {
        this.mnemonics2 = mnemonics2;
    }

    public String getMnemonics3() {
        return mnemonics3;
    }

    public void setMnemonics3(String mnemonics3) {
        this.mnemonics3 = mnemonics3;
    }
}
