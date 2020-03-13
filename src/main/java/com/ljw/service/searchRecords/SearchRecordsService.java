package com.ljw.service.searchRecords;

import java.util.List;

/**
 * @Classname SearchRecordsSreice
 * @Description TODO
 * @Date 2020/3/12 16:42
 * @Created by 刘俊伟
 */
public interface SearchRecordsService {
    //查询热搜词
    List<String> getHotWords();
}
