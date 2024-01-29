package com.yimo.samples.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yimo.samples.dto.CommodityDTO;
import com.yimo.samples.entity.StockEntity;

/**
 * 服务类
 *
 * @author coder
 * @date 2024-01-12
 */
public interface IStockService extends IService<StockEntity> {

    /**
     * 扣减库存
     */
    void decreaseStock(CommodityDTO commodityDTO);
}
