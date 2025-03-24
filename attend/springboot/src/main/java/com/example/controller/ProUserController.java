package com.example.controller;

import com.example.common.Result;
import com.example.entity.ProUser;
import com.example.service.ProUserService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 高级用户模块前端请求的入口
* */
@RestController
@RequestMapping("/prouser")
public class ProUserController {

    @Autowired
    private ProUserService proUserService;

    //新增
    @PostMapping("/add")//RequestBody json数据
    public Result add(@RequestBody ProUser proUser){
        proUserService.add(proUser);
        return Result.success();
    }

    //分页查询
    @GetMapping("/selectPage")
    public Result selectPage(ProUser proUser,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<ProUser> pageInfo = proUserService.selectPage(proUser,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    //更新
    @PutMapping("/update")
    public Result update(@RequestBody ProUser proUser){
        proUserService.updateById(proUser);
        return Result.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        proUserService.deleteById(id);
        return Result.success();
    }
}
