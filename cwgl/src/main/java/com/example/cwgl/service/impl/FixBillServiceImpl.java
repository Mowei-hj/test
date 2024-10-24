package com.example.cwgl.service.impl;

import com.example.cwgl.dao.FixBillMapper;
import com.example.cwgl.entity.FixBill;
import com.example.cwgl.entity.Payway;
import com.example.cwgl.service.FixBillService;
import com.example.cwgl.utils.PageModel;
import com.example.cwgl.utils.Result;
import com.example.cwgl.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class FixBillServiceImpl implements FixBillService {

    @Resource
    private FixBillMapper mapper;

    @Override
    public int add(FixBill bill) {
        return mapper.add(bill);
    }

    @Override
    public int update(FixBill bill) {
        return mapper.update(bill);
    }

    @Override
    public int del(int id) {
        return mapper.del(id);
    }

    @Override
    public Result<FixBill> findByWhere(PageModel model) {
        try {
            List<FixBill> bills = mapper.findByWhere(model);
            if (bills.size() >= 0) {
                Result<FixBill> result = ResultUtil.success(bills);
                result.setTotal(mapper.getTotalByWhere(model));
                if (result.getTotal() == 0) {
                    result.setMsg("没有查到相关数据");
                } else {
                    result.setMsg("数据获取成功");
                }
                return result;
            } else {
                return ResultUtil.unSuccess("获取数据失败！");
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @Override
    public Result<FixBill> findByWhereNoPage(FixBill bill) {
        try {
            List<FixBill> bills = mapper.findByWhereNoPage(bill);
            if (bills.size() >= 0) {
                Result<FixBill> result = ResultUtil.success(bills);
                result.setMsg("数据获取成功");
                return result;
            } else {
                return ResultUtil.unSuccess("没有找到符合条件的属性！");
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @Override
    public List<Payway> getAllPayways() {
        return mapper.getAllPayways();
    }

    @Override
    public List<Map<String, Float>> getMonthlyInfo(PageModel<FixBill> model) {
        return mapper.getMonthlyInfo(model);
    }
}
