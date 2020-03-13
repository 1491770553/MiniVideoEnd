package com.ljw.service.bgm.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.dao.BgmMapper;
import com.ljw.domain.Bgm;
import com.ljw.service.bgm.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname BgmServiceImpl
 * @Description TODO
 * @Date 2020/3/10 12:20
 * @Created by 刘俊伟
 */
@Service
public class BgmServiceImpl implements BgmService {
    @Autowired
    private BgmMapper bgmMapper;


    /**
     * @Author 远
     * @Description 查询全部bgm音乐
     * @Param
     * @return
     **/
    @Override
    public List<Bgm> QueryBgm() {
        return bgmMapper.selectList(null);
    }

    @Override
    public Bgm QueryByid(QueryWrapper<Bgm> queryWrapper) {
        return bgmMapper.selectOne(queryWrapper);
    }


}