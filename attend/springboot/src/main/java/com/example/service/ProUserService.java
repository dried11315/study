package com.example.service;

import com.example.entity.Account;
import com.example.entity.ProUser;
import com.github.pagehelper.PageInfo;


public interface ProUserService {

    void add(ProUser proUser);

    PageInfo<ProUser> selectPage(ProUser proUser, Integer pageNum, Integer pageSize);

    void updateById(ProUser proUser);

    void deleteById(Integer id);

    Account login(Account account);

    void updatePassword(Account account);
}
