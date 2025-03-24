package com.example.service.Impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Leave;
import com.example.mapper.LeaveMapper;
import com.example.service.LeaveService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public void add(Leave leave) {

        if (ObjectUtil.isEmpty(leave.getState())) {
            leave.setState("未处理");
        }

        leave.setTime(DateUtil.now());
        leaveMapper.insert(leave);
    }

    @Override
    public PageInfo<Leave> selectPage(Leave leave, Integer pageNum, Integer pageSize) {
        List<Leave> list;
        PageHelper.startPage(pageNum,pageSize);
        if (ObjectUtil.isNotEmpty(leave.getName())){
            list = leaveMapper.selectByName(leave.getName());
        } else {
            list = leaveMapper.selectAll();
        }
        return PageInfo.of(list);
    }

    @Override
    public void updateById(Leave leave) {
        leaveMapper.updateById(leave);
    }

    @Override
    public void deleteById(Integer id) {
        leaveMapper.deleteById(id);
    }
}
