package com.example.cwgl.service;


import com.example.cwgl.entity.Bill;
import com.example.cwgl.entity.FixBill;
import com.example.cwgl.entity.Payway;
import com.example.cwgl.utils.PageModel;
import com.example.cwgl.utils.Result;

import java.util.List;
import java.util.Map;

public interface FixBillService {

    int add(FixBill bill);

    int update(FixBill bill);

    int del(int id);

    Result<FixBill> findByWhere(PageModel model);

    Result<FixBill> findByWhereNoPage(FixBill bill);

    List<Payway> getAllPayways();

    List<Map<String,Float>>  getMonthlyInfo(PageModel<FixBill> model);

}
