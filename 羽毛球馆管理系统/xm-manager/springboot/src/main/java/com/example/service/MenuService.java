package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Menu;
import com.example.mapper.MenuMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 健身菜谱表业务处理
 **/
@Service
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 新增
     */
    public void add(Menu menu) {
        menu.setTime(DateUtil.now());
        menuMapper.insert(menu);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        menuMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            menuMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Menu menu) {
        menuMapper.updateById(menu);
    }

    /**
     * 根据ID查询
     */
    public Menu selectById(Integer id) {
        return menuMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Menu> selectAll(Menu menu) {
        return menuMapper.selectAll(menu);
    }

    /**
     * 分页查询
     */
    public PageInfo<Menu> selectPage(Menu menu, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Menu> list = menuMapper.selectAll(menu);
        return PageInfo.of(list);
    }

}