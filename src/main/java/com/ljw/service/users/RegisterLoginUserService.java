package com.ljw.service.users;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.domain.Users;

/**
 * @Classname RegisterUser
 * @Description TODO 注册用户
 * @Date 2020/3/7 14:26
 * @Created by 刘俊伟
 */

public interface RegisterLoginUserService {

    //注册用户
    int RegisterUser(Users users);
    //判断是否存在用户
    boolean isUsers(QueryWrapper<Users> usersQueryWrapper);
    //用户登录
    Users Login(QueryWrapper<Users> usersQueryWrapper);

}
