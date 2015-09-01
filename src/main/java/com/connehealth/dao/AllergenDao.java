package com.connehealth.dao;

import com.connehealth.entities.Allergen;
import com.connehealth.entities.Icd10;
import com.connehealth.entities.PatientProblem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public interface AllergenDao {
    public List<Allergen> getAllergens(@Param(value = "type") String type, @Param(value = "term") String term);

    public Allergen getAllergen(@Param(value = "type") String type, @Param(value = "code") String code);
    public Allergen getAllergenById(Long id);

    public Long deleteAllergenById(Long id);
    public void deleteAllergens();

    public Long createAllergen(Allergen allergen);

    public int updateAllergen(Allergen allergen);

}
