package com.ljw.apiController.applet;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.domain.Users;
import com.ljw.domain.UsersReport;
import com.ljw.service.users.impl.UsersUpServiceImpl;
import com.ljw.util.JSONResultUtil;
import com.ljw.util.RedisAPI;
import com.ljw.vo.PublisherVideo;
import com.ljw.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Classname UsersApiController
 * @Description TODO
 * @Date 2020/3/7 14:41
 * @Created by 刘俊伟
 */
@RestController
@RequestMapping("Users")
@PropertySource("classpath:application.yml")
@Api(value = "用户高级功能接口", tags = "用户文件上传等等的Controller")
public class UsersUpApiController {
    @Autowired
    private UsersUpServiceImpl usersUpService;

    @Value("${upfile.fileSpace}")
    private String fileSpace;

    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description 用户上传头像api
     * @Param Users 已登录用户信息
     **/
    @PostMapping("UploadFace")
    @ApiOperation(value = "用户上传头像api", notes = "用户上传头像")
    @ApiImplicitParam(name = "usersId", value = "这是一个用户id", required = true, dataType = "String", paramType = "query")
    public JSONResultUtil UploadFace(String usersId, @RequestParam("file") MultipartFile[] files) throws Exception {
        //数据库相对路径  face：头像
        String uploadpathDB = "/" + usersId + "face";
        if(usersId == null){
            return JSONResultUtil.errorMsg("用户id不能为空！");
        }
        if (files != null && files.length > 0) {
            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;
            //获取文件名字
            String fileName = files[0].getOriginalFilename();
            try {
                if (!StringUtils.isEmpty(fileName)) {
                    //文件上传最终保存路径（绝对路径）
                    String finalFacePath = fileSpace + uploadpathDB + "/" + fileName;
                    //设置数据库保存路径
                    uploadpathDB += ("/" + fileName);

                    File outFile = new File(finalFacePath);
                    //判断路径是否存在
                    if (outFile.getParentFile()!=null||!outFile.getParentFile().isDirectory()) {
                        //创建父文件夹  创建路径
                        outFile.getParentFile().mkdir();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                    //修改用户信息
                    QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id",usersId);
                    Users users = new Users();
                    users.setId(usersId);
                    users.setFaceImage(uploadpathDB);
                    usersUpService.UploadUsers(users,queryWrapper);
                    return JSONResultUtil.ok(uploadpathDB);
                }else{
                    return JSONResultUtil.errorMsg("上传出错！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
            return JSONResultUtil.errorMsg("文件出错！");
        }
        return JSONResultUtil.errorMsg("上传出错！");
    }
    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description
     * @Param Users 已登录用户信息
     **/
    @PostMapping("queryUsersId")
    @ApiOperation(value = "首页用户信息查询api", notes = "用户查询")
    @ApiImplicitParam(name = "usersId", value = "这是一个用户id", required = true, dataType = "String", paramType = "query")
    public JSONResultUtil SelectById(String usersId){
        Users users = usersUpService.QueryUsers(usersId);
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users,usersVo);
        return JSONResultUtil.ok(usersVo);
    }

    @PostMapping("/queryPublisher")
    public JSONResultUtil queryPublisher(String loginUserId, String videoId,
                                          String publishUserId) throws Exception {

        if (StringUtils.isEmpty(publishUserId)) {
            return JSONResultUtil.errorMsg("");
        }

        // 1. 查询视频发布者的信息
        Users userInfo = usersUpService.queryUserInfo(publishUserId);
        UsersVo publisher = new UsersVo();
        BeanUtils.copyProperties(userInfo, publisher);

        // 2. 查询当前登录者和视频的点赞关系
        boolean userLikeVideo = usersUpService.isUserLikeVideo(loginUserId, videoId);

        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);

        return JSONResultUtil.ok(bean);
    }
    @ApiOperation(value="查询用户信息", notes="查询用户信息的接口")
    @ApiImplicitParam(name="userId", value="用户id", required=true,
            dataType="String", paramType="query")
    @PostMapping("/query")
    public JSONResultUtil query(String userId, String fanId) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            return JSONResultUtil.errorMsg("用户id不能为空...");
        }

        Users userInfo = usersUpService.queryUserInfo(userId);
        UsersVo userVO = new UsersVo();
        BeanUtils.copyProperties(userInfo, userVO);

        userVO.setFollow(usersUpService.queryIfFollow(userId, fanId));

        return JSONResultUtil.ok(userVO);
    }


    @PostMapping("/beyourfans")
    public JSONResultUtil beyourfans(String userId, String fanId) throws Exception {

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(fanId)) {
            return JSONResultUtil.errorMsg("");
        }

        usersUpService.saveUserFanRelation(userId, fanId);

        return JSONResultUtil.ok("关注成功...");
    }
    @PostMapping("/dontbeyourfans")
    public JSONResultUtil dontbeyourfans(String userId, String fanId) throws Exception {

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(fanId)) {
            return JSONResultUtil.errorMsg("");
        }

        usersUpService.deleteUserFanRelation(userId, fanId);

        return JSONResultUtil.ok("取消关注成功...");
    }
    @PostMapping("/reportUser")
    public JSONResultUtil reportUser(@RequestBody UsersReport usersReport) throws Exception {

        // 保存举报信息
        usersUpService.reportUser(usersReport);

        return JSONResultUtil.errorMsg("举报成功...有你平台变得更美好...");
    }
}