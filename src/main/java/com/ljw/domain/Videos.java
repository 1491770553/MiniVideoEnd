package com.ljw.domain;

import java.util.Date;
import lombok.Data;

/**
 * @Classname Videos
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Data
public class Videos {
    private String id;

    /**
    * 发布者id
    */
    private String userId;

    /**
    * 用户使用音频的信息
    */
    private String audioId;

    /**
    * 视频描述
    */
    private String videoDesc;

    /**
    * 视频存放的路径
    */
    private String videoPath;

    /**
    * 视频秒数
    */
    private Double videoSeconds;

    /**
    * 视频宽度
    */
    private Integer videoWidth;

    /**
    * 视频高度
    */
    private Integer videoHeight;

    /**
    * 视频封面图
    */
    private String coverPath;

    /**
    * 喜欢/赞美的数量
    */
    private Long likeCounts;

    /**
    * 视频状态：
1、发布成功
2、禁止播放，管理员操作
    */
    private Integer status;

    /**
    * 创建时间
    */
    private Date createTime;
}