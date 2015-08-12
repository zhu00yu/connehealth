package com.connehealth.dao;

import com.connehealth.entities.Practice;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PracticeDao {
    public List<Practice> getPractices();

    public Practice getPracticeById(Long id);
    public Practice getPracticeByName(String name);

    public Long deletePracticeById(Long id);

    public Long createPractice(Practice practice);

    public int updatePractice(Practice practice);

    public void deletePractices();
}
