package com.connehealth.dao;

import com.connehealth.entities.MasterData;
import com.connehealth.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhuyu on 2015/8/5.
 */
public interface MasterDataDao {
    public List<MasterData> getMasterData(String type);

    public MasterData getMasterDataById(Long id);
    public MasterData getMasterDataByKey(@Param(value = "type")String type, @Param(value = "key")String key);
}
