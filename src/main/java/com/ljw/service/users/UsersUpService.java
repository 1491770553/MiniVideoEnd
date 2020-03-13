package com.ljw.service.users;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.domain.Users;
import com.ljw.domain.UsersReport;

/**
 * @Classname UsersUpService
 * @Description TODO 用户高级service层
 * @Date 2020/3/8 14:53
 * @Created by 刘俊伟
 */

public interface UsersUpService {
    //修改用户信息
    int UploadUsers(Users users, QueryWrapper<Users> usersQueryWrapper);
    //查询用户
    Users QueryUsers(String UsersId);
    //查询用户
    Users queryUserInfo(String userId);
    //判断是视频主什么关系
    boolean isUserLikeVideo(String userId, String videoId);
    //查询是否是粉丝
    boolean queryIfFollow(String userId, String fanId);

    void saveUserFanRelation(String userId, String fanId);
    //取消关注
    void deleteUserFanRelation(String userId, String fanId);
    //举报
    void reportUser(UsersReport userReport);
}
