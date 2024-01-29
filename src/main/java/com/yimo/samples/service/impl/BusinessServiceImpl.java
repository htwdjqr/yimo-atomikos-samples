package com.yimo.samples.service.impl;

import com.yimo.samples.dto.BusinessDTO;
import com.yimo.samples.dto.CommodityDTO;
import com.yimo.samples.dto.OrderDTO;
import com.yimo.samples.service.IBusinessService;
import com.yimo.samples.service.IOrderService;
import com.yimo.samples.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 这是描述
 *
 * @author 会跳舞的机器人
 * @date 2024/1/25
 */
@Slf4j
@Service
public class BusinessServiceImpl implements IBusinessService {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IStockService stockService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String handleBusiness(BusinessDTO businessDTO) {
        // 创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        orderService.createOrder(orderDTO);

        // 模拟抛出异常
        // int i = 1 / 0;

        // 扣库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        stockService.decreaseStock(commodityDTO);

        // 模拟两个操作都执行完再抛出异常
        // int i = 1 / 0;
        return "success";
    }
}
