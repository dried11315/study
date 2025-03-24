package com.example.service;

import com.example.entity.Attend;
import com.github.pagehelper.PageInfo;

public interface AttendService {
    void add(Attend attend);

    PageInfo<Attend> selectPage(Attend attend, Integer pageNum, Integer pageSize);

    void updateById(Attend attend);

    void deleteById(Integer id);
}
