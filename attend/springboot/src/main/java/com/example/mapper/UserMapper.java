package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user( username, password, name, sex, title, role, avatar)" +
            "values ( #{username}, #{password}, #{name}, #{sex}, #{title}, #{role}, #{avatar})")
    void insert(User user);


    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    @Select("select * from user")
    List<User> selectAll();

    @Select("select * from user where name like concat('%', #{name}, '%')")
    List<User> selectByName(String name);

    @Update("update user set" +
            " username=#{username}, password=#{password}, name=#{name}, avatar=#{avatar}, role=#{role}, sex=#{sex}, title=#{title}" +
            " where id = #{id}")
    void updateById(User user);

    @Delete("delete from user where id = #{id}")
    int deleteById(Integer id);
}
