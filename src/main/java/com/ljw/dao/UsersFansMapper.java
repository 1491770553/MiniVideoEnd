package com.ljw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.domain.UsersFans;
import org.springframework.stereotype.Repository;

/**
 * @Classname UsersFansMapper
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Repository
public interface UsersFansMapper extends BaseMapper<UsersFans> {
    int deleteByPrimaryKey(String id);

    int insert(UsersFans record);

    int insertSelective(UsersFans record);

    UsersFans selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersFans record);

    int updateByPrimaryKey(UsersFans record);
}