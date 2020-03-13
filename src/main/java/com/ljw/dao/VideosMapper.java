package com.ljw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.domain.Videos;
import com.ljw.vo.VideosVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname VideosMapper
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Repository
public interface VideosMapper extends BaseMapper<Videos> {
    int deleteByPrimaryKey(String id);

    int insert(Videos record);

    int insertSelective(Videos record);

    Videos selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Videos record);

    int updateByPrimaryKey(Videos record);

    List<VideosVO> queryAllVideos(@Param("videoDesc")String videoDesc,@Param("userId")String userId);
}