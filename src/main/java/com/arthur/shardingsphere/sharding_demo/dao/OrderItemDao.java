package com.arthur.shardingsphere.sharding_demo.dao;

import com.arthur.shardingsphere.sharding_demo.po.OrderItemPo;
import com.arthur.shardingsphere.sharding_demo.po.OrderPo;
import com.arthur.shardingsphere.sharding_demo.utils.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/6 20:15
 */
@Mapper
public interface OrderItemDao {

    @Select("SELECT * FROM t_order_item where order_id = #{orderId}")
    OrderPo getById(Long orderId);

    @Insert("<script>INSERT INTO t_order_item (order_id) VALUES" +
            "<foreach collection=\"list\" item=\"order\" index=\"index\" separator=\",\">" +
            "(#{ order.orderId})" +
            "</foreach></script>")
    @Options(useGeneratedKeys=true,keyProperty="orderItemId")
    int insertBatch(List<OrderItemPo> orderItemPoList);





    @InsertProvider(type= SqlProvider.class,method="insertSql")
    @Options(useGeneratedKeys=true,keyProperty="orderItemId")
    int insert(Object data);

    @UpdateProvider(type = SqlProvider.class, method = "updateSql")
    int update(String key, Object data);

    @DeleteProvider(type = SqlProvider.class,method = "deleteSql")
    int delete(String tableName, String keyName, String keyValue);
}
