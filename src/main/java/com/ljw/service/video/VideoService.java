package com.ljw.service.video;

import com.ljw.domain.Comments;
import com.ljw.domain.Videos;
import com.ljw.util.PagedResult;

/**
 * @Classname VideoService
 * @Description TODO
 * @Date 2020/3/11 11:54
 * @Created by 刘俊伟
 */
public interface VideoService {

    //用户发布视频
    String instOneVideo(Videos video);
    //修改视频信息
    int Update(Videos videos);
    //分页查询视频列表
    PagedResult getAllPageDate( Videos videos,Integer isSaveRecord,Integer page,Integer pageSize);
    //留言分页
    PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
    //用户喜欢/点赞视频
    public void userLikeVideo(String userId, String videoId, String videoCreaterId);
    //用户不喜欢/取消点赞视频
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);
    //用户留言
    public void saveComment(Comments comment);
    //我收藏的视频
    PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);
    //我关注的人视频
    PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize);

   }
