package com.connehealth.dao;

import com.connehealth.entities.Drug;
import com.connehealth.entities.Icd10;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public interface DrugDao {
    public List<Drug> getDrugs(@Param(value = "term") String term);

    public Drug getDrug(@Param(value = "approveCode") String approveCode, @Param(value = "standardCode") String standardCode);
    public Drug getDrugById(Long id);
}
