package com.example.controller;

import com.example.common.Result;
import com.example.entity.Leave;
import com.example.service.LeaveService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    //新增
    @PostMapping("/add")//RequestBody json数据
    public Result add(@RequestBody Leave leave){
        leaveService.add(leave);
        return Result.success();
    }


    //分页查询
    @GetMapping("/selectPage")
    public Result selectPage(Leave leave,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<Leave> pageInfo = leaveService.selectPage(leave,pageNum,pageSize);
        return Result.success(pageInfo);
    }
    
    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Leave leave){
        leaveService.updateById(leave);
        return Result.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        leaveService.deleteById(id);
        return Result.success();
    }
}
