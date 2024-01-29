package com.yimo.samples.dao.stock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yimo.samples.entity.StockEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *  Mapper 接口
 *
 * @author coder
 * @date 2024-01-12
 */
@Mapper
public interface StockDao extends BaseMapper<StockEntity> {

    /**
     * 扣减商品库存
     *
     * @Param: commodityCode 商品code  count扣减数量
     * @Return:
     */
    int decreaseStock(@Param("commodityCode") String commodityCode, @Param("count") Integer count);


}
