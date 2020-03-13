package com.ljw.domain;

import java.util.Date;
import lombok.Data;

/**
 * @Classname UsersReport
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Data
public class UsersReport {
    private String id;

    /**
    * 被举报用户id
    */
    private String dealUserId;

    private String dealVideoId;

    /**
    * 类型标题，让用户选择，详情见 枚举
    */
    private String title;

    /**
    * 内容
    */
    private String content;

    /**
    * 举报人的id
    */
    private String userid;

    /**
    * 举报时间
    */
    private Date createDate;
}