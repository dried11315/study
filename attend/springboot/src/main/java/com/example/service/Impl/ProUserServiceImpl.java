package com.example.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.ProUser;
import com.example.exception.CustomException;
import com.example.mapper.ProUserMapper;
import com.example.service.ProUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProUserServiceImpl implements ProUserService {

    @Autowired
    private ProUserMapper proUserMapper;

    @Override
    public void add(ProUser proUser) {
        //做一些逻辑处理
        ProUser dbProUser = proUserMapper.selectByUsername(proUser.getUsername());
        if (ObjectUtil.isNotEmpty(dbProUser)){
            throw new CustomException("用户名已存在");
        }
        if(ObjectUtil.isEmpty(proUser.getPassword())){
            proUser.setPassword("123456");
        }
        proUser.setRole("PROUSER");

        proUserMapper.insert(proUser);
    }

    //分页查询
    @Override
    public PageInfo<ProUser> selectPage(ProUser proUser,Integer pageNum, Integer pageSize) {
        List<ProUser> list;
        PageHelper.startPage(pageNum,pageSize);
        if (ObjectUtil.isNotEmpty(proUser.getName())){
            list = proUserMapper.selectByName(proUser.getName());
        } else {
            list = proUserMapper.selectAll();
        }

        return PageInfo.of(list);
    }

    @Override
    public void updateById(ProUser proUser) {
        proUserMapper.updateById(proUser);
    }

    @Override
    public void deleteById(Integer id) {
        proUserMapper.deleteById(id);
    }

    //登录
    @Override
    public ProUser login(Account account) {
        ProUser dbProUser = proUserMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbProUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbProUser.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        return dbProUser;
    }

    //修改密码
    @Override
    public void updatePassword(Account account) {
        ProUser dbProUser = proUserMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbProUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbProUser.getPassword())) {
            throw new CustomException("原密码错误");
        }
        dbProUser.setPassword(account.getNewPassword());
        proUserMapper.updateById(dbProUser);
    }

}
