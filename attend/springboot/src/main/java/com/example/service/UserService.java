package com.example.service;

import com.example.entity.Account;
import com.example.entity.User;
import com.github.pagehelper.PageInfo;


public interface UserService {

    void add(User user);

    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);

    void updateById(User user);

    void deleteById(Integer id);

    Account login(Account account);

    void register(Account account);

    void updatePassword(Account account);
}
