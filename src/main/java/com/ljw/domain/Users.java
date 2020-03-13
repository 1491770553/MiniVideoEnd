package com.ljw.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname Users
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Data
@ApiModel(value = "用户对象",description = "这是用户对象")
public class Users {
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",name="username",example = "ljw",required = true)
    private String username;

    /**
    * 密码
    */
    @ApiModelProperty(value = "密码",name="password",example = "123456",required = true)
    private String password;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号",name="number",example = "13873548963",required = true)
    private String number;

    /**
    * 我的头像，如果没有默认给一张
    */
    @ApiModelProperty(hidden = true)
    private String faceImage;

    /**
    * 昵称
    */
    @ApiModelProperty(value = "昵称",name="nickname",example = "哈喽",required = true)
    private String nickname;

    /**
    * 我的粉丝数量
    */
    @ApiModelProperty(hidden = true)
    private Integer fansCounts;

    /**
    * 我关注的人总数
    */
    @ApiModelProperty(hidden = true)
    private Integer followCounts;

    /**
    * 我接受到的赞美/收藏 的数量
    */
    @ApiModelProperty(hidden = true)
    private Integer receiveLikeCounts;
}