package com.connehealth.dao;

import com.connehealth.entities.Practice;
import com.connehealth.entities.PracticeFile;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/7.
 */
public interface PracticeFileDao {
    public List<PracticeFile> getPracticeFiles();

    public PracticeFile getPracticeFileById(Long id);
    public PracticeFile getPracticeFileByName(String name);

    public Long deletePracticeFileById(Long id);

    public Long createPracticeFile(PracticeFile practiceFile);

    public int updatePracticeFile(PracticeFile practiceFile);

    public void deletePracticeFiles();
}
