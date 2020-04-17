package com.arthur.shardingsphere.sharding_demo.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/6 23:35
 */
@Data
@Accessors(chain = true)
@Component
@Scope("prototype")
public class OrderAndItemVo {
    private Long orderId;
    private String orderTitle;
    private Integer orderStateId;
    private String createTime;
    private String updateTime;
    private Integer isDelete;

    private Long orderItemId;
    private String orderItemTitle;

    private String orderStateTitle;
}
