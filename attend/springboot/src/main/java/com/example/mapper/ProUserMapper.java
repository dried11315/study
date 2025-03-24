package com.example.mapper;

import com.example.entity.Admin;
import com.example.entity.ProUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProUserMapper {

    @Insert("insert into prouser( username, password, name, sex, title, role, avatar)" +
            "values ( #{username}, #{password}, #{name}, #{sex}, #{title}, #{role}, #{avatar})")
    void insert(ProUser proUser);

    @Select("select * from prouser where username = #{username}")
    ProUser selectByUsername(String username);

    @Select("select * from prouser")
    List<ProUser> selectAll();

    @Select("select * from prouser where name like concat('%', #{name}, '%')")
    List<ProUser> selectByName(String name);

    @Update("update prouser set" +
            " username=#{username}, password=#{password}, name=#{name}, avatar=#{avatar}, role=#{role}, sex=#{sex}, title=#{title}" +
            " where id = #{id}")
    void updateById(ProUser proUser);

    @Delete("delete from prouser where id = #{id}")
    int deleteById(Integer id);
}
