package com.vermouth.sell.repository;

import com.vermouth.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    private final String OPENID = "123456";

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setBuyerAddress("xxx");
        orderMaster.setBuyerOpenid("123456");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster = repository.save(orderMaster);
        Assert.assertNotNull(orderMaster.getOrderId());
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest request = new PageRequest(0, 1);
        Page<OrderMaster> page = repository.findByBuyerOpenid(OPENID, request);
        System.out.println(page.getTotalElements());            //2，返回数据库中的总数
    }

}