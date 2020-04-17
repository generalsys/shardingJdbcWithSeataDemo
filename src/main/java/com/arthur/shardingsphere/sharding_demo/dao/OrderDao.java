package com.arthur.shardingsphere.sharding_demo.dao;

import com.arthur.shardingsphere.sharding_demo.po.OrderPo;
import com.arthur.shardingsphere.sharding_demo.utils.SqlProvider;
import com.arthur.shardingsphere.sharding_demo.vo.OrderAndItemVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/6 20:15
 */
@Mapper
public interface OrderDao {

    @Select("SELECT * FROM t_order where order_id = #{orderId}")
    OrderPo getById(Long orderId);

    @Select("<script>SELECT * FROM t_order o join t_order_item i " +
            "on o.order_id=i.order_id " +
            "where o.order_id in " +
            "<foreach collection=\"list\" item=\"orderId\" index=\"index\" open=\"(\" close=\")\" separator=\",\">" +
            "#{orderId}" +
            "</foreach></script>")
    List<OrderAndItemVo> getByIdList(List<Long> orderIdList);

    @Select("<script>SELECT * FROM t_order o join t_order_item i " +
            "on o.order_id=i.order_id " +
            "where o.order_state_id in " +
            "<foreach collection=\"list\" item=\"orderStateId\" index=\"index\" open=\"(\" close=\")\" separator=\",\">" +
            "#{orderStateId}" +
            "</foreach></script>")
    List<OrderAndItemVo> getByStateId(List<Integer> stateIdList);

    @Select("SELECT * FROM t_order o,t_order_item i " +
            "where o.order_id = i.order_id " +
            "and o.order_id = #{orderId}")
    List<OrderAndItemVo> getOrderAndItem(Long orderId);

    @Select("SELECT * FROM t_order o,t_order_item i,t_order_state s " +
            "where o.order_id = i.order_id " +
            "and o.order_state_id=s.order_state_id " +
            "and o.order_id = #{orderId}")
    List<OrderAndItemVo> getOrderAndItemAndState(Long orderId);

    @Insert("<script>INSERT INTO t_order (order_title) VALUES" +
            "<foreach collection=\"list\" item=\"order\" index=\"index\" separator=\",\">" +
            "(#{order.orderTitle})" +
            "</foreach></script>")
    @Options(useGeneratedKeys=true,keyProperty="orderId")
    int insertBatch(List<OrderPo> orderPoList);

    Integer getOrderAvg();

    List<OrderPo> getOrderPage();


    @InsertProvider(type= SqlProvider.class,method="insertSql")
    @Options(useGeneratedKeys=true,keyProperty="orderId")
    int insert(Object data);

    @UpdateProvider(type = SqlProvider.class, method = "updateSql")
    int update(String key, Object data);

    @DeleteProvider(type = SqlProvider.class,method = "deleteSql")
    int delete(String tableName, String keyName, String keyValue);
}
