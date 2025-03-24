package com.example.mapper;


import com.example.entity.Attend;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttendMapper {

    //签到
    @Insert("insert into attend(id, name, state, time, title, role)" +
            "values (#{id}, #{name},  #{state}, #{time}, #{title}, #{role})")
    void insert(Attend attend);

    //查询所有
    @Select("select * from attend")
    List<Attend> selectAll();

    //根据名称查询
    @Select("select * from attend where name like concat('%', #{name}, '%')")
    List<Attend> selectByName(String name);


    //删除
    @Delete("delete from attend where id = #{id}")
    int deleteById(Integer id);

    //根据ID查询
    @Select("select * from attend where id = #{id}")
    void findById(Attend attend);

    //更新
    @Update("update attend set name = #{name}, state = #{state} where id = #{id}")
    void updateById(Attend attend);
}
