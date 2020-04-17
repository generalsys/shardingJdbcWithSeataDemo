package com.arthur.shardingsphere.sharding_demo.service;

import com.arthur.shardingsphere.sharding_demo.dao.OrderDao;
import com.arthur.shardingsphere.sharding_demo.dao.OrderItemDao;
import com.arthur.shardingsphere.sharding_demo.po.OrderItemPo;
import com.arthur.shardingsphere.sharding_demo.po.OrderPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/6 21:00
 */
@Slf4j
@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public void insertOrderItem(OrderItemPo orderItemPo) {
        log.info("插入order item开始：");
        orderItemDao.insert(orderItemPo);
        log.info("插入order item结束！");
    }

    public void insertBatch(List<OrderItemPo> orderItemPoList) {
        log.info("批量插入order item开始：");
        orderItemDao.insertBatch(orderItemPoList);
        log.info("批量插入order item结束！");
    }


}
