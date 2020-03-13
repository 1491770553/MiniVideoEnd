package com.ljw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.domain.Users;
import org.springframework.stereotype.Repository;

/**
 * @Classname UsersMapper
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Repository
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * @Description: 用户受喜欢数累加
     */
    public void addReceiveLikeCount(String userId);

    /**
     * @Description: 用户受喜欢数累减
     */
    public void reduceReceiveLikeCount(String userId);

    /**
     * @Description: 增加粉丝数
     */
    public void addFansCount(String userId);

    /**
     * @Description: 增加关注数
     */
    public void addFollersCount(String userId);

    /**
     * @Description: 减少粉丝数
     */
    public void reduceFansCount(String userId);

    /**
     * @Description: 减少关注数
     */
    public void reduceFollersCount(String userId);

}