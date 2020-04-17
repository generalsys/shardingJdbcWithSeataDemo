package com.arthur.shardingsphere.sharding_demo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/6 20:09
 */
@Data
@Accessors(chain = true)
@Component
@Scope("prototype")
public class OrderItemPo {
    private Long orderItemId;
    private String orderItemTitle;
    private Long orderId;
    private String createTime;
    private String updateTime;
    private Integer isDelete;
}
