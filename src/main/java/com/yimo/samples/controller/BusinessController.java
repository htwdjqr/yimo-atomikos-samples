package com.yimo.samples.controller;

import com.yimo.samples.dto.BusinessDTO;
import com.yimo.samples.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下单业务控制层
 *
 * @author 会跳舞的机器人
 * @date 2024/1/25
 */
@RestController
@RequestMapping("/business")
@Slf4j
public class BusinessController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessController.class);
    @Autowired
    private IBusinessService businessService;

    /**
     * 模拟用户购买商品下单业务逻辑流程
     *
     * @Param:
     * @Return:
     */
    @PostMapping("/buy")
    String handleBusiness(@RequestBody BusinessDTO businessDTO) {
        LOGGER.info("请求参数：{}", businessDTO.toString());
        String result = "fail";
        try {
            result = businessService.handleBusiness(businessDTO);
        } catch (Exception e) {
            LOGGER.error("下单业务异常", e);
        }
        return result;
    }

}
