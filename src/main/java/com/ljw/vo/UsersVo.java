package com.ljw.vo;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @Classname UsersVo
 * @Description TODO vo users
 * @Date 2020/3/7 22:29
 * @Created by 刘俊伟
 */
@Data
public class UsersVo {

    private String id;

    private String userToken;

    private boolean isFollow;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     * 不传递出去+
     */
    @JsonIgnore
    private String password;
    /**
     * 手机号
     */
    private String number;

    /**
     * 我的头像，如果没有默认给一张
     */
    private String faceImage;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 我的粉丝数量
     */
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    private Integer followCounts;

    /**
     * 我接受到的赞美/收藏 的数量
     */
    private Integer receiveLikeCounts;
}