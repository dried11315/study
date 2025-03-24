package com.example.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;

import com.example.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //新增用户
    @Override
    public void add(User user) {
        //做一些逻辑处理
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (ObjectUtil.isNotEmpty(dbUser)){
            throw new CustomException("用户名已存在");
        }
        if(ObjectUtil.isEmpty(user.getPassword())){
            user.setPassword("123456");
        }
        if(ObjectUtil.isEmpty(user.getName())){
            user.setName(user.getUsername());
        }
        if(ObjectUtil.isEmpty(user.getTitle())){
            user.setTitle("普通用户");
        }
        user.setRole("USER");

        userMapper.insert(user);
    }

    //分页查询
    @Override
    public PageInfo<User> selectPage(User user,Integer pageNum, Integer pageSize) {
        List<User> list;
        PageHelper.startPage(pageNum,pageSize);
        if (ObjectUtil.isNotEmpty(user.getName())){
            list = userMapper.selectByName(user.getName());
        } else {
            list = userMapper.selectAll();
        }

        return PageInfo.of(list);
    }

    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    //登录
    @Override
    public User login(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        return dbUser;
    }

    //注册
    @Override
    public void register(Account account) {
        User user = new User();
        user.setUsername(account.getUsername());
        user.setPassword(account.getPassword());
        add(user);
    }

    //修改密码
    @Override
    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("原密码错误");
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }

}
