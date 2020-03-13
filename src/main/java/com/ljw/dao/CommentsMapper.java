package com.ljw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.domain.Comments;
import org.springframework.stereotype.Repository;

/**
 * @Classname CommentsMapper
 * @Description TODO
 * @Date 2020/3/7 14:12
 * @Created by 刘俊伟
 */
@Repository
public interface CommentsMapper extends BaseMapper<Comments> {
    int deleteByPrimaryKey(String id);

    int insert(Comments record);

    int insertSelective(Comments record);

    Comments selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Comments record);

    int updateByPrimaryKey(Comments record);
}