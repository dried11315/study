package com.example.controller;

import com.example.common.Result;
import com.example.entity.Attend;
import com.example.entity.Attend;
import com.example.service.AttendService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attend")
public class AttendController {

    @Autowired
    private AttendService attendService;

    //新增
    @PostMapping("/add")//RequestBody json数据
    public Result add(@RequestBody Attend attend){
        attendService.add(attend);
        return Result.success();
    }


    //分页查询
    @GetMapping("/selectPage")
    public Result selectPage(Attend attend,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<Attend> pageInfo = attendService.selectPage(attend,pageNum,pageSize);
        return Result.success(pageInfo);
    }
    
    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Attend attend){
        attendService.updateById(attend);
        return Result.success();
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        attendService.deleteById(id);
        return Result.success();
    }
}
