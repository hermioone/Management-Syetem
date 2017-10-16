package com.vermouth.sell.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vermouth.sell.dataobject.OrderDetail;
import com.vermouth.sell.enums.OrderStatusEnums;
import com.vermouth.sell.enums.PayStatusEnum;
import com.vermouth.sell.serializer.DateToLongSerializer;
import com.vermouth.sell.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)          //转json字符串时去掉那些值为null的元素
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    //默认为0:新下单
    private Integer orderStatus = OrderStatusEnums.NEW.getCode();
    //默认为0：未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    //创建时间
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;
    //更新时间
    @JsonSerialize(using = DateToLongSerializer.class)              //序列化时，会执行这个类里的操作
    private Date updateTime;


    private List<OrderDetail> orderDetailList;

    @JsonIgnore             //对象转成json格式，会忽略掉这个方法
    public OrderStatusEnums getOrderStatusEnums() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnums.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
