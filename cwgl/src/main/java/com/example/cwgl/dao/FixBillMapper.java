package com.example.cwgl.dao;


import com.example.cwgl.entity.Bill;
import com.example.cwgl.entity.FixBill;
import com.example.cwgl.entity.Payway;
import com.example.cwgl.utils.PageModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FixBillMapper {

    int add(FixBill bill);

    int update(FixBill bill);

    int del(int id);

    List<FixBill> findByWhere(PageModel<FixBill> model);
    List<FixBill> listAll();

    List<FixBill> findByWhereNoPage(FixBill model);

    int getTotalByWhere(PageModel<FixBill> model);

    List<Map<String,Float>> getMonthlyInfo(PageModel<FixBill> model);

    List<Payway> getAllPayways();
}
