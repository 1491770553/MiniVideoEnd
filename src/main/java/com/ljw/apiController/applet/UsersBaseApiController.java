package com.ljw.apiController.applet;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.domain.Users;
import com.ljw.service.users.impl.RegisterLoginUserServiceImpl;
import com.ljw.util.JSONResultUtil;
import com.ljw.util.MD5;
import com.ljw.util.RedisAPI;
import com.ljw.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Classname UsersApiController
 * @Description TODO
 * @Date 2020/3/7 14:41
 * @Created by 刘俊伟
 */
@RestController
@RequestMapping("Users")
@Api(value = "用户基础功能接口", tags = "用户注册和登录、注销的Controller")
public class UsersBaseApiController {
    @Autowired
    private RegisterLoginUserServiceImpl registerUserService;
    @Autowired
    private RedisAPI redisAPI;

    /**
     * @return
     * @Author 刘俊伟
     * @Description //TODO 通用代码-用于生成无状态session
     **/
    public UsersVo getUsersVo(Users users) {
        String uniqueToken = UUID.randomUUID().toString();
        redisAPI.set("USERS_REDIS_SESSION:" + users.getId(), 1000 * 60 * 30, uniqueToken);
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users, usersVo);
        usersVo.setUserToken(uniqueToken);
        return usersVo;
    }

    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description 用户注册
     * @Param Users 注册用户信息
     **/
    @PostMapping("regist")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    public JSONResultUtil regist(@RequestBody Users users) throws Exception {
        //判断用是否为空
        if (StringUtils.isEmpty(users.getUsername()) || StringUtils.isEmpty(users.getPassword()) || StringUtils.isEmpty(users.getNickname()) || StringUtils.isEmpty(users.getNumber())) {
            return JSONResultUtil.errorMsg("唉！每个信息输入不能为空！还要我教你输入吗？");
        }
        //判断用户是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", users.getUsername());
        if (!registerUserService.isUsers(queryWrapper)) {
            return JSONResultUtil.errorMsg("哎呀！手速慢了,用户名居然被别人抢了！");
        }
        //保存用户
        Users usersVis = new Users();
        usersVis.setUsername(users.getUsername());
        usersVis.setPassword(MD5.getMd5(users.getPassword()));//md5加密
        usersVis.setFaceImage("../resource/images/head.png");//设置默认头像
        usersVis.setFansCounts(0);//粉丝数量
        usersVis.setReceiveLikeCounts(0); //我接受到的赞美/收藏 的数量
        usersVis.setFollowCounts(0);//我关注的数量
        usersVis.setNickname(users.getNickname());//设置昵称
        usersVis.setNumber(users.getNumber());//手机号
        String ID = UUID.randomUUID().toString().replaceAll("-", "");
        usersVis.setId(ID);
        int result = registerUserService.RegisterUser(usersVis);
        if (result > 0)
            return JSONResultUtil.ok();
        else
            return JSONResultUtil.errorMsg("由于您太帅了，服务器被您帅炸了");
    }

    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description 用户登录
     * @Param Users 登录用户信息
     **/
    @PostMapping("login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public JSONResultUtil login(@RequestBody Users users) {
        //判断用是否为空
        if (StringUtils.isEmpty(users.getUsername()) || StringUtils.isEmpty(users.getPassword())){
            return JSONResultUtil.errorMsg("还想登录！密码账号都不输？");
        }
        //判断用户是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();//条件构造器
        queryWrapper.eq("username", users.getUsername());//用户名是否一致
        queryWrapper.eq("password", MD5.getMd5(users.getPassword()));//验证MD5密码是否一致
        Users videoUsers = registerUserService.Login(queryWrapper);//查询用户
        if (videoUsers == null || videoUsers.getId() == null) {
            return JSONResultUtil.errorMsg("颜值过高，所以服务器认为你的账号密码错误");
        } else {
            UsersVo usersVo = getUsersVo(videoUsers);
            return JSONResultUtil.ok(usersVo);
        }
    }
    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description 用户注销
     * @Param Users 已登录用户信息
     **/
    @PostMapping("OutLogin")
    @ApiOperation(value = "用户注销", notes = "用户注销")
    @ApiImplicitParam(name="usersId",value = "这是一个用户id",required = true,dataType = "String",paramType = "query")
    public JSONResultUtil OutLogin(String usersId) throws Exception{
        //判断用是否为空
        if (StringUtils.isEmpty(usersId)){
            return JSONResultUtil.errorMsg("用户未登录！或者session过期");
        }
        //删除redis中的值
        redisAPI.delete("USERS_REDIS_SESSION:"+usersId);
        //判断redis中是否还存在
        boolean exist = redisAPI.exist("USERS_REDIS_SESSION:"+usersId);
        if(!exist){
            return JSONResultUtil.ok("注销成功！");
        }else{
            return JSONResultUtil.errorMsg("你把系统给帅的异常了?请重试");
        }
    }
}