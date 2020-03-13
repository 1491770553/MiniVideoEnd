package com.ljw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.domain.Bgm;
import org.springframework.stereotype.Repository;

/**
 * @Classname BgmMapper
 * @Description TODO
 * @Date 2020/3/7 14:12
 * @Created by 刘俊伟
 */
@Repository
public interface BgmMapper extends BaseMapper<Bgm> {
    int deleteByPrimaryKey(String id);

    int insert(Bgm record);

    int insertSelective(Bgm record);

    Bgm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Bgm record);

    int updateByPrimaryKey(Bgm record);
}