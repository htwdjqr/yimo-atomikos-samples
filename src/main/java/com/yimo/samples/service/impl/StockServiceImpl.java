package com.yimo.samples.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimo.samples.dao.stock.StockDao;
import com.yimo.samples.dto.CommodityDTO;
import com.yimo.samples.entity.StockEntity;
import com.yimo.samples.service.IStockService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author coder
 * @date 2024-01-12
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockDao, StockEntity> implements IStockService {

    @Override
    public void decreaseStock(CommodityDTO commodityDTO) {
        int stock = baseMapper.decreaseStock(commodityDTO.getCommodityCode(), commodityDTO.getCount());
        if (stock <= 0) {
            throw new RuntimeException("扣减库存失败");
        }
    }
}
