package com.connehealth.dao;

import com.connehealth.entities.AdverseReaction;
import com.connehealth.entities.Allergen;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public interface AdverseReactionDao {
    public List<AdverseReaction> getAdverseReactions(@Param(value = "type") String type, @Param(value = "term") String term);

    public AdverseReaction getAdverseReaction(@Param(value = "type") String type, @Param(value = "code") String code);
    public AdverseReaction getAdverseReactionById(Long id);

    public Long deleteAdverseReactionById(Long id);
    public void deleteAdverseReactions();

    public Long createAdverseReaction(AdverseReaction adverseReaction);

    public int updateAdverseReaction(AdverseReaction adverseReaction);

}
