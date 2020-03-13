package com.ljw.domain;

import java.util.Date;

import lombok.Data;

/**
 * @Classname Comments
 * @Description TODO
 * @Date 2020/3/7 14:12
 * @Created by 刘俊伟
 */
@Data
public class Comments  {
    private String id;

    private String fatherCommentId;

    private String toUserId;

    /**
    * 视频id
    */
    private String videoId;

    /**
    * 留言者，评论的用户id
    */
    private String fromUserId;

    /**
    * 评论内容
    */
    private String comment;

    private Date createTime;
}