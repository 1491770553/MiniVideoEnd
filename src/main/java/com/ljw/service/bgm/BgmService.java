package com.ljw.service.bgm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.domain.Bgm;

import java.util.List;

/**
 * @Classname BgmService
 * @Description TODO
 * @Date 2020/3/10 12:19
 * @Created by 刘俊伟
 */
public interface BgmService {
    //查询所有的bgm音乐
    List<Bgm> QueryBgm();
    //查询所有的bgm音乐
    Bgm QueryByid(QueryWrapper<Bgm> queryWrapper);
}
