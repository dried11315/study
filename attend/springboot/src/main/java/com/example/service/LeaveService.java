package com.example.service;

import com.example.entity.Leave;
import com.github.pagehelper.PageInfo;

public interface LeaveService {
    void add(Leave leave);

    PageInfo<Leave> selectPage(Leave leave, Integer pageNum, Integer pageSize);

    void updateById(Leave leave);

    void deleteById(Integer id);
}
