package com.example.service.Impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Attend;

import com.example.mapper.AttendMapper;
import com.example.service.AttendService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AttendServiceImpl implements AttendService {

    @Autowired
    private AttendMapper attendMapper;

    @Override
    public void add(Attend attend) {

        if (ObjectUtil.isEmpty(attend.getState())) {
            attend.setState("正常");
        }

        attend.setTime(DateUtil.now());
        attendMapper.insert(attend);
    }

    @Override
    public PageInfo<Attend> selectPage(Attend attend, Integer pageNum, Integer pageSize) {
        List<Attend> list;
        PageHelper.startPage(pageNum,pageSize);
        if (ObjectUtil.isNotEmpty(attend.getName())){
            list = attendMapper.selectByName(attend.getName());
        } else {
            list = attendMapper.selectAll();
        }

        return PageInfo.of(list);
    }

    @Override
    public void updateById(Attend attend) {
        attendMapper.updateById(attend);
    }

    @Override
    public void deleteById(Integer id) {
        attendMapper.deleteById(id);
    }
}
