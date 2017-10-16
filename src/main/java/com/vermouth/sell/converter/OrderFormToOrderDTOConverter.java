package com.vermouth.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vermouth.sell.dataobject.OrderDetail;
import com.vermouth.sell.datatransferobject.OrderDTO;
import com.vermouth.sell.enums.ResultEnum;
import com.vermouth.sell.exception.SellException;
import com.vermouth.sell.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

public class OrderFormToOrderDTOConverter  {
    public static OrderDTO convert(OrderForm orderForm) {

        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //把json格式的字符串转成List
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
