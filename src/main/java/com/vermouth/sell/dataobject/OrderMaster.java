package com.vermouth.sell.dataobject;

import com.vermouth.sell.enums.OrderStatusEnums;
import com.vermouth.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
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
    private Date createTime;
    //更新时间
    private Date updateTime;

//    @Transient              //在数据库对应时会把这个字段忽略
//    private List<OrderDetail> orderDetailList;
}
