package com.example.mapper;


import com.example.entity.Attend;
import com.example.entity.Leave;
import com.example.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LeaveMapper {

    //请假
    @Insert("insert into beleave(id, name, style, state, time, title, role)" +
            "values ( #{id}, #{name}, #{style}, #{state}, #{time}, #{title}, #{role})")
    void insert(Leave leave);

    //查询所有
    @Select("select * from beleave")
    List<Leave> selectAll();

    //根据名称查询
    @Select("select * from beleave where name like concat('%', #{name}, '%')")
    List<Leave> selectByName(String name);


    //删除
    @Delete("delete from beleave where id = #{id}")
    int deleteById(Integer id);

    //根据ID查询
    @Select("select * from beleave where id = #{id}")
    void findById(Leave leave);

    //更新
    @Update("update beleave set name=#{name},style=#{style},state=#{state} where id = #{id}")
    void updateById(Leave leave);
}
