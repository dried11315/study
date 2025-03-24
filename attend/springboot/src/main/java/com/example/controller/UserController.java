package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 普通用户模块前端请求的入口
* */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //新增
    @PostMapping("/add")//RequestBody json数据
    public Result add(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }

    //分页查询
    @GetMapping("/selectPage")
    public Result selectPage(User user,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<User> pageInfo = userService.selectPage(user,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    //更新
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.success();
    }
}
