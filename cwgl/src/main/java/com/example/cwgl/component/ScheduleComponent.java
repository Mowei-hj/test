package com.example.cwgl.component;

import cn.hutool.core.date.DateUtil;
import com.example.cwgl.dao.FixBillMapper;
import com.example.cwgl.entity.Bill;
import com.example.cwgl.entity.FixBill;
import com.example.cwgl.service.BillService;
import com.example.cwgl.service.FixBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduleComponent {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Autowired
    private FixBillService fixBillService;
    @Autowired
    private BillService billService;

    @Resource
    private FixBillMapper fixBillMapper;

    @PostConstruct
    public void fixBillHandler() {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = DateUtil.thisDayOfMonth();
                    //获取今天的日期 如果是第一天才执行
                    if (i == 1) {
                        List<FixBill> fixBills = fixBillMapper.listAll();
                        if (fixBills != null && fixBills.size() > 0) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String now = sdf.format(new Date());
                            for (FixBill fixBill : fixBills) {
                                Bill bill = new Bill();
                                bill.setTitle(fixBill.getTitle());
                                bill.setUserid(fixBill.getUserid());
                                bill.setMoney(fixBill.getMoney());
                                bill.setTypeid(fixBill.getTypeid());
                                bill.setRemark(fixBill.getRemark());
                                bill.setPaywayid(fixBill.getPaywayid());
                                bill.setTime(now);
                                billService.add(bill);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 0, 1, TimeUnit.DAYS);
    }
}
