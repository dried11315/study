package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.ProUserService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class WebController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ProUserService proUserService;
    @Autowired
    private UserService userService;


    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        if ("ADMIN".equals(account.getRole())) {
            return Result.success(adminService.login(account));
        }
        if ("PROUSER".equals(account.getRole())) {
            return Result.success(proUserService.login(account));
        }
        if ("USER".equals((account.getRole()))) {
            return Result.success(userService.login(account));
        }
        return Result.success();
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        userService.register(account);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if ("ADMIN".equals(account.getRole())) {
            adminService.updatePassword(account);
        }
        if ("PROUSER".equals((account.getRole()))) {
            proUserService.updatePassword(account);
        }
        if ("USER".equals((account.getRole()))) {
            userService.updatePassword(account);
        }
        return Result.success();
    }

}
