package com.yimo.samples.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimo.samples.dao.order.OrderDao;
import com.yimo.samples.dto.OrderDTO;
import com.yimo.samples.entity.OrderEntity;
import com.yimo.samples.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 服务实现类
 *
 * @author coder
 * @date 2024-01-12
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements IOrderService {


    @Override
    public OrderEntity createOrder(OrderDTO orderDTO) {
        //生成订单号
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        //生成订单
        OrderEntity tOrder = new OrderEntity();
        BeanUtils.copyProperties(orderDTO, tOrder);
        tOrder.setCount(orderDTO.getOrderCount());
        tOrder.setAmount(orderDTO.getOrderAmount().doubleValue());

        baseMapper.createOrder(tOrder);
        return tOrder;
    }
}
