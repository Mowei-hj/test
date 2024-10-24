package com.example.mapper;

import com.example.entity.Menu;

import java.util.List;

/**
 * 操作menu相关数据接口
*/
public interface MenuMapper {

    /**
      * 新增
    */
    int insert(Menu menu);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Menu menu);

    /**
      * 根据ID查询
    */
    Menu selectById(Integer id);

    /**
      * 查询所有
    */
    List<Menu> selectAll(Menu menu);

}