package com.ljw.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.domain.Comments;
import com.ljw.vo.CommentsVO;

public interface CommentsMapperCustom extends BaseMapper<Comments> {
	
	public List<CommentsVO> queryComments(String videoId);
}