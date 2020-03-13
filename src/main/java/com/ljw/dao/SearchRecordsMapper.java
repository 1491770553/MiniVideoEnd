package com.ljw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.domain.SearchRecords;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname SearchRecordsMapper
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Repository
public interface SearchRecordsMapper extends BaseMapper<SearchRecords> {
    int deleteByPrimaryKey(String id);

    int insert(SearchRecords record);

    int insertSelective(SearchRecords record);

    SearchRecords selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SearchRecords record);

    int updateByPrimaryKey(SearchRecords record);

    List<String> selectHotCountDesc();
}