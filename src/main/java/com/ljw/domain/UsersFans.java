package com.ljw.domain;

import lombok.Data;

/**
 * @Classname UsersFans
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Data
public class UsersFans {
    private String id;

    /**
    * 用户
    */
    private String userId;

    /**
    * 粉丝
    */
    private String fanId;
}