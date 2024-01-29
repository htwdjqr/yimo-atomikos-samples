package com.yimo.samples.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yimo.samples.dto.OrderDTO;
import com.yimo.samples.entity.OrderEntity;

/**
 * 服务类
 *
 * @author coder
 * @date 2024-01-12
 */
public interface IOrderService extends IService<OrderEntity> {
    /**
     * 创建订单
     */
    OrderEntity createOrder(OrderDTO orderDTO);

}
