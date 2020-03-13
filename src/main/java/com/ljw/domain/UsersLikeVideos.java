package com.ljw.domain;

import lombok.Data;

/**
 * @Classname UsersLikeVideos
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Data
public class UsersLikeVideos {
    private String id;

    /**
    * 用户
    */
    private String userId;

    /**
    * 视频
    */
    private String videoId;
}