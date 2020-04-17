package com.arthur.shardingsphere.sharding_demo.service;

import com.arthur.shardingsphere.sharding_demo.dao.OrderDao;
import com.arthur.shardingsphere.sharding_demo.po.OrderItemPo;
import com.arthur.shardingsphere.sharding_demo.po.OrderPo;

import com.arthur.shardingsphere.sharding_demo.utils.SpringUtil;
import com.arthur.shardingsphere.sharding_demo.vo.OrderAndItemVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@Slf4j
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderDao orderDao;

    @Test
    public void insertOrder() {
        //插入数据
        List<OrderPo> orderPoList = new ArrayList<OrderPo>();
        for (int i = 0; i < 2; i++) {
            OrderPo orderPo = SpringUtil.getBean(OrderPo.class).setOrderTitle("test order "+ i);
            orderService.insertOrder(orderPo);
            orderPoList.add(orderPo);
        }
        for (OrderPo orderPo : orderPoList) {
            OrderItemPo orderItemPo = SpringUtil.getBean(OrderItemPo.class)
                    .setOrderItemTitle("test item "+orderPo.getOrderId())
                    .setOrderId(orderPo.getOrderId());
            orderItemService.insertOrderItem(orderItemPo);

            orderItemPo = SpringUtil.getBean(OrderItemPo.class)
                    .setOrderItemTitle("test item "+orderPo.getOrderId())
                    .setOrderId(orderPo.getOrderId());
            orderItemService.insertOrderItem(orderItemPo);
        }

        //查询数据
        for (OrderPo orderPo : orderPoList) {
            orderService.getOrderAndItem(orderPo.getOrderId());
        }
    }

    @Test
    public void testSeata() {
        orderService.insertOrderSeataTest();


    }


}