package com.connehealth.dao;

import com.connehealth.entities.Icd10;
import com.connehealth.entities.PersistentLogin;
import com.connehealth.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public interface Icd10Dao {
    public List<Icd10> getIcds(@Param(value = "term") String term);

    public Icd10 getIcd(@Param(value = "icdNo") String icdNo, @Param(value = "additionalNo") String additionalNo);
    public Icd10 getIcdById(Long id);
}
