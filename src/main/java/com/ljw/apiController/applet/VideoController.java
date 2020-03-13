package com.ljw.apiController.applet;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.domain.Bgm;
import com.ljw.domain.Comments;
import com.ljw.domain.Videos;
import com.ljw.enums.VideoStatusEnum;
import com.ljw.service.bgm.impl.BgmServiceImpl;
import com.ljw.service.searchRecords.impl.SearchRecordsServiceImpl;
import com.ljw.service.video.impl.VideoServiceImpl;
import com.ljw.util.*;
import io.swagger.annotations.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Classname Video
 * @Description TODO
 * @Date 2020/3/10 13:37
 * @Created by 刘俊伟
 */
@RestController
@Api(value = "影音功能接口", tags = "查询bgm/用户上传视频。。。等等的api接口")
@RequestMapping("video")
public class VideoController {
    @Autowired
    private BgmServiceImpl bgmService;
    @Autowired
    private VideoServiceImpl videoService;
    @Autowired
    private SearchRecordsServiceImpl searchRecordsService;

    @Value("${upfile.fileSpace}")
    private String fileSpace;
    @Value("${FFMPEG}")
    private String FFMPEG;
    @Value("${Page_Size}")
    private Integer Page_Size;

    @PostMapping("QueryBgm")
    @ApiOperation(value = "查询bgm Api", notes = "查询bgm")
    public JSONResultUtil GetBgm() {
        List<Bgm> bgms = bgmService.QueryBgm();
        return JSONResultUtil.ok(bgms);
    }

    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description 视频上传api
     * @Param Users 已登录用户信息
     **/
    @PostMapping(value = "Upload", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "用户上传视频api", notes = "用户上传视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐id", required = false,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false,
                    dataType = "String", paramType = "form")
    })
    public JSONResultUtil Upload(String userId, String bgmId, Double videoSeconds, int videoWidth, int videoHeight
            , String desc
            , @ApiParam(value = "短视频", required = true) MultipartFile file) throws Exception {
        //数据库相对路径  face：头像
        String uploadpathDB = "/" + userId + "video";
        //封面
        String CoverPathDB =  "/" + userId + "video";
        // 文件上传的最终保存路径
        String finalVideoPath = "";
        //废弃视频的路径
        String abandon = "/ZZDelete";

        if (file != null) {
            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;
            //获取文件名字
            String fileName = file.getOriginalFilename();
            //获取文件名字除去后缀
            String fileNamePre = fileName.split("\\.")[0];
            try {
                if (!StringUtils.isEmpty(fileName)) {

                    //文件上传最终保存路径（绝对路径）
                    String finaVideolPath = fileSpace + uploadpathDB + "/" + fileName;
                    //保存到数据的数据
                    CoverPathDB = CoverPathDB+"/"+fileNamePre+".jpg";
                    abandon = abandon+"/"+fileNamePre+".mp4";
                    //设置数据库保存路径
                    uploadpathDB += ("/" + fileName);

                    File outFile = new File(finaVideolPath);
                    //判断路径是否存在
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹  创建路径
                        outFile.getParentFile().mkdir();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } else {
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
        } else {
            return JSONResultUtil.errorMsg("视频文件为空！");
        }
        // 判断bgmId是否为空，如果不为空，
        // 那就查询bgm的信息，并且合并视频，生产新的视频
        if (!StringUtils.isEmpty(bgmId)) {
            finalVideoPath = fileSpace+uploadpathDB;
            QueryWrapper<Bgm> bgmQueryWrapper = new QueryWrapper<>();
            bgmQueryWrapper.eq("id",bgmId);
            Bgm bgm = bgmService.QueryByid(bgmQueryWrapper);

            //背景音乐
            String mp3InputPath = fileSpace+ bgm.getPath();
            //Param1文件上传的视频-- Param2 -文件夹废弃视频的路径
            //videoSeconds视频秒数---finalVideoPath文件上传最终路径
            //生成一个废弃的无声音视频，但是这个视频会进行和bgm进行整合  --abandon废弃文件路径
            FfmpegUtil.transferClearMusic(finalVideoPath,fileSpace+abandon);
            System.out.println("==================================================================");
            System.out.println("11111111");
            System.out.println(finalVideoPath);
            System.out.println(fileSpace+abandon);
            //新的视频路径
            String videoOutputName = UUID.randomUUID().toString() + ".mp4";

            uploadpathDB = "/" + userId + "video" + "/" + videoOutputName;
            FfmpegUtil.transferBgm(fileSpace+abandon,fileSpace+uploadpathDB,mp3InputPath,String.valueOf(videoSeconds-1));
            System.out.println("==================================================================");
            System.out.println(fileSpace+abandon);
            System.out.println(fileSpace+uploadpathDB);
            System.out.println(mp3InputPath);
            System.out.println(videoSeconds.toString());

        }
        finalVideoPath = fileSpace+uploadpathDB;
        //对视频进行截图
        FfmpegUtil.transfer(finalVideoPath, fileSpace + CoverPathDB,"0");
        //保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds(videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadpathDB);
        video.setCoverPath(CoverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setLikeCounts((long)0);
        video.setCreateTime(new Date());
        String videoId =  videoService.instOneVideo(video);
       if (videoId!=null){
           return JSONResultUtil.ok(videoId);
       }else{
           return JSONResultUtil.errorMsg("上传失败");
       }
       }

    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description 视频上传api
     * @Param Users 已登录用户信息
     **/
    @PostMapping(value = "Uploadcover")
    @ApiOperation(value = "用户上传封面", notes = "用户上传封面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form"),
    })
    public JSONResultUtil Uploadcover(String videoId,String userId
            , @ApiParam(value = "视频封面", required=true) MultipartFile file) throws Exception {
        //数据库相对路径
        String uploadpathDB = "/" + userId + "video";


        if (file != null) {
            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;
            //获取文件名字
            String fileName = file.getOriginalFilename();

            try {
                if (!StringUtils.isEmpty(fileName)) {
                    //文件上传最终保存路径（绝对路径）
                    String finaCoverPath = fileSpace + uploadpathDB + "/" + fileName;

                    //设置数据库保存路径
                    uploadpathDB += ("/" + fileName);

                    File outFile = new File(finaCoverPath);
                    //判断路径是否存在
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹  创建路径
                        outFile.getParentFile().mkdir();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } else {
                    return JSONResultUtil.errorMsg("上传出错！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return JSONResultUtil.errorMsg("上传出错...");
            } finally {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
        } else {
            return JSONResultUtil.errorMsg("视频文件为空！");
        }

        //保存视频信息到数据库
        Videos videos = new Videos();
        videos.setId(videoId);
        videos.setCoverPath(uploadpathDB);
        videoService.Update(videos);
        if (videoId!=null){
            return JSONResultUtil.ok(videoId);
        }else{
            return JSONResultUtil.errorMsg("上传失败");
        }
    }

    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description 查询视频列表api
     * @Param Users 已登录用户信息 isSaveRecord 1---需要保存     0 或者 null --- 不需要保存
     **/
    @PostMapping(value = "showAll")
    /*@ApiOperation(value = "用户上传封面", notes = "用户上传封面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form"),
    })*/
    public JSONResultUtil Uploadcover(@RequestBody Videos videos,Integer isSaveRecord, Integer page, Integer pageSize) throws Exception {
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 5;
        }

        PagedResult result = videoService.getAllPageDate(videos, isSaveRecord, page, pageSize);
        return JSONResultUtil.ok(result);
    }

    /**
     * @return json类型
     * @Author 刘俊伟
     * @Description 查询视频列表api
     * @Param Users 已登录用户信息 isSaveRecord 1---需要保存     0 或者 null --- 不需要保存
     **/
    @PostMapping(value = "hot")
    public JSONResultUtil hot() throws Exception {

        return JSONResultUtil.ok(searchRecordsService.getHotWords());
    }

    @PostMapping("/getVideoComments")
    public JSONResultUtil getVideoComments(String videoId, Integer page, Integer pageSize) throws Exception {

        if (StringUtils.isEmpty(videoId)) {
            return JSONResultUtil.ok();
        }

        // 分页查询视频列表，时间顺序倒序排序
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        PagedResult list = videoService.getAllComments(videoId, page, pageSize);

        return JSONResultUtil.ok(list);
    }
    //新增喜欢
    @PostMapping(value="/userLike")
    public JSONResultUtil userLike(String userId, String videoId, String videoCreaterId)
            throws Exception {
        videoService.userLikeVideo(userId, videoId, videoCreaterId);
        return JSONResultUtil.ok();
    }
    //取消喜欢
    @PostMapping(value="/userUnLike")
    public JSONResultUtil userUnLike(String userId, String videoId, String videoCreaterId) throws Exception {
        videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
        return JSONResultUtil.ok();
    }
    @PostMapping("/saveComment")
    public JSONResultUtil saveComment(@RequestBody Comments comment,
                                       String fatherCommentId, String toUserId) throws Exception {

        comment.setFatherCommentId(fatherCommentId);
        comment.setToUserId(toUserId);

        videoService.saveComment(comment);
        return JSONResultUtil.ok();
    }

    /**
     * @Description: 我收藏(点赞)过的视频列表
     */
    @PostMapping("/showMyLike")
    public JSONResultUtil showMyLike(String userId, Integer page, Integer pageSize) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            return JSONResultUtil.ok();
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 6;
        }

        PagedResult videosList = videoService.queryMyLikeVideos(userId, page, pageSize);

        return JSONResultUtil.ok(videosList);
    }

    /**
     * @Description: 我关注的人发的视频
     */
    @PostMapping("/showMyFollow")
    public JSONResultUtil showMyFollow(String userId, Integer page) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            return JSONResultUtil.ok();
        }

        if (page == null) {
            page = 1;
        }

        int pageSize = 6;

        PagedResult videosList = videoService.queryMyFollowVideos(userId, page, pageSize);

        return JSONResultUtil.ok(videosList);
    }

}