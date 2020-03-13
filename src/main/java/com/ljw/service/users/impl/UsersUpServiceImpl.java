package com.ljw.service.users.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ljw.dao.UsersFansMapper;
import com.ljw.dao.UsersLikeVideosMapper;
import com.ljw.dao.UsersMapper;
import com.ljw.dao.UsersReportMapper;
import com.ljw.domain.Users;
import com.ljw.domain.UsersFans;
import com.ljw.domain.UsersLikeVideos;
import com.ljw.domain.UsersReport;
import com.ljw.service.users.UsersUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Classname UsersUpServiceImpl
 * @Description TODO
 * @Date 2020/3/8 14:54
 * @Created by 刘俊伟
 */
@Service

public class UsersUpServiceImpl implements UsersUpService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;
    @Autowired
    private UsersFansMapper usersFansMapper;
    @Autowired
    private UsersReportMapper usersReportMapper;
    @Override
    public int UploadUsers(Users users, QueryWrapper<Users> usersQueryWrapper) {
        //修改用户信息
        return usersMapper.update(users,usersQueryWrapper);
    }

    @Override
    public Users QueryUsers(String UsersId) {
        //根据id查询用户信息
        return usersMapper.selectById(UsersId);
    }

    @Override
    public Users queryUserInfo(String userId) {
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id",userId);
        return usersMapper.selectOne(usersQueryWrapper);
    }

    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return false;
        }

        QueryWrapper<UsersLikeVideos> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("video_id", videoId);

        List<UsersLikeVideos> list = usersLikeVideosMapper.selectList(queryWrapper);
        if (list != null && list.size() >0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {
        QueryWrapper<UsersFans> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("fan_id", fanId);

        List<UsersFans> list = usersFansMapper.selectList(queryWrapper);

        if (list != null && !list.isEmpty() && list.size() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public void saveUserFanRelation(String userId, String fanId) {
        String relId = UUID.randomUUID().toString();

        UsersFans userFan = new UsersFans();
        userFan.setId(relId);
        userFan.setUserId(userId);
        userFan.setFanId(fanId);

        usersFansMapper.insert(userFan);

        usersMapper.addFansCount(userId);
        usersMapper.addFollersCount(fanId);
    }

    @Override
    public void deleteUserFanRelation(String userId, String fanId) {
        QueryWrapper<UsersFans> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("fan_id", fanId);
        usersFansMapper.delete(queryWrapper);

        usersMapper.reduceFansCount(userId);
        usersMapper.reduceFollersCount(fanId);
    }

    @Override
    public void reportUser(UsersReport userReport) {
        String urId = UUID.randomUUID().toString();
        userReport.setId(urId);
        userReport.setCreateDate(new Date());
        usersReportMapper.insert(userReport);
    }
}