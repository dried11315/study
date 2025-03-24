package com.example.mapper;

import com.example.entity.Admin;
import com.example.entity.ProUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {


    //新增
    @Insert("insert into admin(id, username, password, name, avatar, role)" +
            "values (#{id}, #{username}, #{password}, #{name}, #{avatar}, #{role})")
    int insert(Admin admin);


    //删除
    @Delete("delete from admin where id = #{id}")
    int deleteById(Integer id);


    //修改信息
    @Update("update admin set username = #{username}, password = #{password}, name = #{name}, avatar = #{avatar}, role = #{role}" +
            "where id = #{id} ")
    int updateById(Admin admin);


    //根据ID查询
    @Select("select * from admin where id = #{id}")
    Admin selectById(Integer id);


    //查询所有
    @Select("select * from admin")
    List<Admin> selectAll();

    //根据名称查询
    @Select("select * from admin where name like concat('%', #{name}, '%')")
    List<Admin> selectByName(String name);
    //查询用户名
    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);

}