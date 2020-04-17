package com.arthur.shardingsphere.sharding_demo.service;

import com.arthur.shardingsphere.sharding_demo.dao.OrderDao;
import com.arthur.shardingsphere.sharding_demo.po.OrderItemPo;
import com.arthur.shardingsphere.sharding_demo.po.OrderPo;
import com.arthur.shardingsphere.sharding_demo.utils.SpringUtil;
import com.arthur.shardingsphere.sharding_demo.vo.OrderAndItemVo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/6 21:00
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemService orderItemService;


    public List<OrderAndItemVo> insertDataTest() {
        //插入order
        List<OrderPo> orderPoList = new ArrayList<OrderPo>();
        for (int i = 0; i < 3; i++) {
            orderPoList.add(new OrderPo().setOrderTitle("test order" + i));
        }
        insertOrderBatch(orderPoList);

        //插入order item
        List<OrderItemPo> orderItemPoList = new ArrayList<OrderItemPo>();
        for (OrderPo orderPo : orderPoList) {
            orderItemPoList.add(new OrderItemPo()
                    .setOrderItemTitle("test item 1")
                    .setOrderId(orderPo.getOrderId()));

            orderItemPoList.add(new OrderItemPo()
                    .setOrderItemTitle("test item 2")
                    .setOrderId(orderPo.getOrderId()));
        }
        orderItemService.insertBatch(orderItemPoList);

        //无分片键查询，产生全库表路由
        //return this.getByStateIdList(orderPoList.stream().map(OrderPo::getOrderStateId).collect(Collectors.toList()));
        //分片键查询
        return this.getByOrderIdList(orderPoList.stream().map(OrderPo::getOrderId).collect(Collectors.toList()));
    }

    @GlobalTransactional
    public void insertOrderSeataTest() {
        log.info("开始Seata分布式事务1:");
        for (int i = 0; i < 5; i++) {
            TransactionTypeHolder.set(TransactionType.BASE);
            OrderPo orderPo = SpringUtil.getBean(OrderPo.class).setOrderTitle("test order 1");
            insertOrder(orderPo);
        }
        log.info("Seata分布式事务1结束!");

        //throw new RuntimeException("模拟异常");
    }

    @GlobalTransactional
    public void updateOrderSeataTest() {
        log.info("开始Seata分布式事务2:");
        TransactionTypeHolder.set(TransactionType.BASE);
        OrderPo orderPo = SpringUtil.getBean(OrderPo.class).setOrderTitle("test order 1");
        insertOrder(orderPo);

        TransactionTypeHolder.set(TransactionType.BASE);
        updateOrder(orderPo.setOrderTitle("test update"));
        log.info("Seata分布式事务2结束!");

        throw new RuntimeException("模拟异常");
    }

    public void insertOrder(OrderPo orderPo) {
        log.info("插入order开始：");
        orderDao.insert(orderPo);
        log.info("插入order结束！");
    }

    public void insertOrderBatch(List<OrderPo> orderPoList) {
        log.info("批量插入order开始：");
        orderDao.insertBatch(orderPoList);
        log.info("批量插入order结束！");
    }

    public void updateOrder(OrderPo orderPo) {
        log.info("更新order开始：");
        orderDao.update("orderId", orderPo);
        log.info("更新order结束！");
    }

    public List<OrderAndItemVo> getOrderAndItem(Long orderId) {
        log.info("获取order-join-item 开始：");
        List<OrderAndItemVo> vo = orderDao.getOrderAndItem(orderId);
        log.info("获取order-join-item 结束！");
        return vo;
    }

    public List<OrderAndItemVo> getByOrderIdList(List<Long> orderIdList) {
        log.info("获取order-join-item IN 开始：");
        List<OrderAndItemVo> vo = orderDao.getByIdList(orderIdList);
        log.info("获取order-join-item IN 结束！");
        return vo;
    }

    public List<OrderAndItemVo> getByStateIdList(List<Integer> stateIdList) {
        log.info("获取order join item 开始：");
        List<OrderAndItemVo> vo = orderDao.getByStateId(stateIdList);
        log.info("获取order join item 结束！");
        return vo;
    }

    public Integer getOrderAvg() {
        log.info("获取AVG平均值开始：");
        Integer r=  orderDao.getOrderAvg();
        log.info("获取AVG平均值结束！");
        return r;
    }

    public List<OrderPo> getOrderPage() {
        log.info("获取Limit 分页开始：");
        List<OrderPo> list=  orderDao.getOrderPage();
        log.info("获取Limit 分页结束！");
        return list;
    }
}
