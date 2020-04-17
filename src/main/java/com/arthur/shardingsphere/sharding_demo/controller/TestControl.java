package com.arthur.shardingsphere.sharding_demo.controller;

import com.arthur.shardingsphere.sharding_demo.po.OrderPo;
import com.arthur.shardingsphere.sharding_demo.service.OrderService;
import com.arthur.shardingsphere.sharding_demo.vo.OrderAndItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/9 18:08
 */
@RestController
@RequestMapping("/sharding/")
public class TestControl {

    @Autowired
    OrderService orderService;

    /**
     * @description: 批量插入数据并查询
     * @param
     * @return: java.util.List<com.arthur.shardingsphere.sharding_demo.vo.OrderAndItemVo>
     */
    @GetMapping("insertTest")
    public List<OrderAndItemVo> insertTest() {
        return orderService.insertDataTest();
    }

    /**
     * @description: 平均值改写演示
     * @param
     * @return: java.lang.Integer
     */
    @GetMapping("avgTest")
    public Integer avgTest() {

        return orderService.getOrderAvg();
    }

    /**
     * @description: 分页改写演示
     * @param
     * @return: java.lang.Integer
     */
    @GetMapping("pageTest")
    public List<OrderPo> pageTest() {

        return orderService.getOrderPage();
    }

    @GetMapping("seataUpdateTest")
    public String seataUpdateTest() {
        orderService.updateOrderSeataTest();
        return "ok";
    }

    @GetMapping("seataInsertTest")
    public String seataInsertTest() {
        orderService.insertOrderSeataTest();
        return "ok";
    }

}
