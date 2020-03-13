package com.ljw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.domain.UsersReport;
import org.springframework.stereotype.Repository;

/**
 * @Classname UsersReportMapper
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Repository
public interface UsersReportMapper extends BaseMapper<UsersReport> {
    int deleteByPrimaryKey(String id);

    int insert(UsersReport record);

    int insertSelective(UsersReport record);

    UsersReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersReport record);

    int updateByPrimaryKey(UsersReport record);
}