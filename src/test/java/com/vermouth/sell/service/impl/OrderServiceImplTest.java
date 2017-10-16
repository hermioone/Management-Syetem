package com.vermouth.sell.service.impl;

import com.vermouth.sell.dataobject.OrderDetail;
import com.vermouth.sell.datatransferobject.OrderDTO;
import com.vermouth.sell.enums.OrderStatusEnums;
import com.vermouth.sell.enums.PayStatusEnum;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYEROPENID = "123456";
    private final String ORDERID = "44263d59b0714365ba280b09392bc488";

    @Test
    public void create() throws Exception {
        for (int i = 0; i < 13; i++) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setBuyerAddress("word卢");
            orderDTO.setBuyerName("大路");
            orderDTO.setBuyerPhone("1234567891011");
            orderDTO.setBuyerOpenid(BUYEROPENID);

            List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
            OrderDetail o1 = new OrderDetail();
            o1.setProductId("12312313123");
            o1.setProductQuantity(2);
            orderDetailList.add(o1);

            OrderDetail o2 = new OrderDetail();
            o2.setProductId("1234567");
            o2.setProductQuantity(10);
            orderDetailList.add(o2);

            orderDTO.setOrderDetailList(orderDetailList);

            OrderDTO result = orderService.create(orderDTO);
        }

        //System.out.println(result);

        //Assert.assertNotNull(result);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        Assert.assertEquals(orderDTO.getOrderId(), ORDERID);
    }

    @Test
    public void findList() throws Exception {
//        PageRequest request = new PageRequest(0, 2);
//        Page<OrderDTO> orderDTOPage = orderService.findList(BUYEROPENID, request);
//        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnums.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnums.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void pay() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.pay(orderDTO);
        Assert.assertEquals(PayStatusEnum.FINISHED.getCode(), result.getPayStatus());
    }

    @Test
    public void list() {
        PageRequest request = new PageRequest(0, 2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

}