package com.ljw.domain;

import lombok.Data;

/**
 * @Classname Bgm
 * @Description TODO
 * @Date 2020/3/7 14:12
 * @Created by 刘俊伟
 */
@Data
public class Bgm {
    private String id;

    private String author;

    private String name;

    /**
    * 播放地址
    */
    private String path;
}