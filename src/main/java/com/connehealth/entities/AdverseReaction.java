package com.connehealth.entities;

/**
 * Created by zhuyu on 2015/8/27.
 */
public class AdverseReaction extends AuditableEntity {
    private String type;
    private String code;
    private String mnemonics;
    private String reaction;
    private Integer level;
    private Long parentId;


    public String getMnemonics() {
        return mnemonics;
    }

    public void setMnemonics(String mnemonics) {
        this.mnemonics = mnemonics;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
