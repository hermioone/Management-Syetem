package com.vermouth.sell.service.impl;

import com.vermouth.sell.converter.OrderMasterToOrderDTOConverter;
import com.vermouth.sell.dataobject.OrderDetail;
import com.vermouth.sell.dataobject.OrderMaster;
import com.vermouth.sell.dataobject.ProductInfo;
import com.vermouth.sell.datatransferobject.CartDTO;
import com.vermouth.sell.datatransferobject.OrderDTO;
import com.vermouth.sell.enums.OrderStatusEnums;
import com.vermouth.sell.enums.PayStatusEnum;
import com.vermouth.sell.enums.ResultEnum;
import com.vermouth.sell.exception.SellException;
import com.vermouth.sell.repository.OrderDetailRepository;
import com.vermouth.sell.repository.OrderMasterRepository;
import com.vermouth.sell.service.OrderService;
import com.vermouth.sell.service.ProductService;
import com.vermouth.sell.service.WebSocket;
import com.vermouth.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(0);
        String orderId = KeyUtil.generateUniqueKey();

        //1.查询商品
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null) {
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            //3.订单详情入库
            orderDetail.setDetailId(KeyUtil.generateUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);         //属性copy

            orderDetailRepository.save(orderDetail);
        }

        //4.订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);   //属性值为null，也会被copy进去，所以orderId和orderAmount应该放在这一条之后设置
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //5.扣库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
            new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());Collectors.toList();
        productService.decreaseStock(cartDTOList);

        //5.发送websockt消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIT);
        }
        OrderDTO orderDTO = OrderMasterToOrderDTOConverter.convert(orderMaster);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult =  orderMasterRepository.save(orderMaster);
        if(updateResult == null) {
           throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //3.返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
           throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
            new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //4.如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.FINISHED.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO pay(OrderDTO orderDTO) {
        //1.判断订单状态和支付状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //2.修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }
}
