package com.example.cwgl.controller;


import com.example.cwgl.entity.Bill;
import com.example.cwgl.entity.FixBill;
import com.example.cwgl.entity.Payway;
import com.example.cwgl.entity.UserInfo;
import com.example.cwgl.service.FixBillService;
import com.example.cwgl.utils.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* description: TODO
* @author zhangsihai
* @date 2020/3/24 15:24
*/
@RestController
@RequestMapping("/fixBills")
public class FixBillController {

    @Resource
    private FixBillService fixbillService;

    /**
     * 适用于统计图
     * @param bill
     * @return
     */
    @RequestMapping("/getBillsToChart")
    public Result<FixBill> findByWhereNoPage(FixBill bill, HttpSession session){
        bill = getHouseBill(bill,session);
        return fixbillService.findByWhereNoPage(bill);
    }

    @RequestMapping("/getBillsByWhere/{type}/{pageNo}/{pageSize}")
    public Result<FixBill> getBillsByWhere(FixBill bill,@PathVariable String type, @PathVariable int pageNo, @PathVariable int pageSize, HttpSession session){
        if("-1".equals(bill.getPayway())){
            bill.setPayway(null);
        }
        bill.setType(type);
        bill = getHouseBill(bill,session);
        PageModel model = new PageModel<>(pageNo,bill);
        model.setPageSize(pageSize);
        return fixbillService.findByWhere(model);
    }

    @RequestMapping("/getBillsByUserid/{userid}/{pageNo}/{pageSize}/{year}/{month}")
    public Result getBillsByUserid(@PathVariable Integer userid, @PathVariable int pageNo, @PathVariable int pageSize, @PathVariable int year, @PathVariable int month){
        FixBill bill = new FixBill();
        bill.setUserid(userid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        bill.setStartTime(year+"-0"+month+"-01");
        try {
            Date date = sdf.parse(year+"-0"+(month+1)+"-01");
            date.setDate(date.getDate()-1);
            bill.setEndTime(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PageModel model = new PageModel<>(pageNo,bill);
        model.setPageSize(pageSize);
        Result result = fixbillService.findByWhere(model);
        List<Map<String,String>> r = fixbillService.getMonthlyInfo(model);
        Map<String,String> map = new HashMap<>();
        for (Map<String,String> m: r) {
            map.put(m.get("typeid"),String.format("%.2f",m.get("sum(money)")));
        }
        result.setData(map);
        return result;
    }

    private FixBill getHouseBill(FixBill bill, HttpSession session) {
        UserInfo currentUser = Config.getSessionUser(session);
        //当登录用户为家主时，查询默认查询全家账单情况
        //当登录用户为普通用户时，仅查询当前用户的账单
        if (currentUser.getRoleid() == 2){
            bill.setHouseid(currentUser.getHouseid());
        }else if (currentUser.getRoleid() == 3){
            bill.setUserid(currentUser.getId());
        }
        return bill;
    }

    @RequestMapping(value = "/addBill",method = RequestMethod.POST) //相当于前段接口
    public Result add(FixBill bill, HttpSession session){
        if (Config.getSessionUser(session)!=null){
            bill.setUserid(Config.getSessionUser(session).getId());
        }
        Utils.log(bill.toString());
        try {
            int num = fixbillService.add(bill);
            if(num>0){
                int billid = bill.getId();
                bill = new FixBill();
                bill.setId(billid);
                return ResultUtil.success("记账成功！", fixbillService.findByWhereNoPage(bill));
//                return ResultUtil.success("记账成功！",bill);
            }else {
                return ResultUtil.unSuccess();
            }
        }catch (Exception e){
            return ResultUtil.error(e);
        }
    }

    @RequestMapping("/updateBill")
    public Result update(FixBill bill, HttpSession session){
        if (Config.getSessionUser(session)!=null){
            bill.setUserid(Config.getSessionUser(session).getId());
        }
        Utils.log(bill.toString());
        try {
            int num = fixbillService.update(bill);
            if(num>0){
                return ResultUtil.success("修改成功！",null);
            }else {
                return ResultUtil.unSuccess();
            }
        }catch (Exception e){
            return ResultUtil.error(e);
        }
    }

    @RequestMapping("/delBill")
    public Result del(int id){
        try {
            int num = fixbillService.del(id);
            if(num>0){
                return ResultUtil.success("删除成功！",null);
            }else {
                return ResultUtil.unSuccess();
            }
        }catch (Exception e){
            return ResultUtil.error(e);
        }
    }


}
