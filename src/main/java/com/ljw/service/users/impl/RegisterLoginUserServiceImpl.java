package com.ljw.service.users.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.dao.UsersMapper;
import com.ljw.domain.Users;
import com.ljw.service.users.RegisterLoginUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Classname RegisterUserServiceImpl
 * @Description TODO 用户登录和注册
 * @Date 2020/3/7 14:28
 * @Created by 刘俊伟
 */
@Service
public class RegisterLoginUserServiceImpl implements RegisterLoginUserService {
    @Resource
    private UsersMapper usersMapper;


    @Override
    public int RegisterUser(Users users) {
        return usersMapper.insert(users);
    }

    @Override
    public boolean isUsers(QueryWrapper<Users> usersQueryWrapper) {
        //查询是否存在
        List<Users> users = usersMapper.selectList(usersQueryWrapper);
        if (users == null || users.isEmpty())
            return true;
        else
            return false;
    }

    @Override
    public Users Login(QueryWrapper<Users> usersQueryWrapper) {
        //查询用户
        Users users = usersMapper.selectOne(usersQueryWrapper);
        if (users == null || users.getId() == null)
            return null;
        else
            return users;
    }
}
