package com.ljw.domain;

import lombok.Data;

/**
 * @Classname SearchRecords
 * @Description TODO
 * @Date 2020/3/7 14:13
 * @Created by 刘俊伟
 */
@Data
public class SearchRecords {
    private String id;

    /**
    * 搜索的内容
    */
    private String content;
}