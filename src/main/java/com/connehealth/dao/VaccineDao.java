package com.connehealth.dao;

import com.connehealth.entities.Drug;
import com.connehealth.entities.Vaccine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public interface VaccineDao {
    public List<Vaccine> getVaccines(@Param(value = "term") String term);

    public Vaccine getVaccine(@Param(value = "code") String code);
    public Vaccine getVaccineById(Long id);
}
