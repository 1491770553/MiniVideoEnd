package com.ljw.service.searchRecords.impl;

import com.ljw.dao.SearchRecordsMapper;
import com.ljw.service.searchRecords.SearchRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname SearchRecordsServiceImpl
 * @Description TODO
 * @Date 2020/3/12 16:42
 * @Created by 刘俊伟
 */
@Service
public class SearchRecordsServiceImpl implements SearchRecordsService {
    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Override
    public List<String> getHotWords() {
        return searchRecordsMapper.selectHotCountDesc();
    }
}
