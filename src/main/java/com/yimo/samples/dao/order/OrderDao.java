package com.yimo.samples.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yimo.samples.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper 接口
 *
 * @author coder
 * @date 2024-01-12
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
    /**
     * 创建订单
     *
     * @Param: order 订单信息
     * @Return:
     */
    void createOrder(@Param("order") OrderEntity order);
}
