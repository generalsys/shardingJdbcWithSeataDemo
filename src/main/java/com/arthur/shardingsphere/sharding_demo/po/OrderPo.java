package com.arthur.shardingsphere.sharding_demo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/6 20:07
 */

@Data
@Accessors(chain = true)
@Component
@Scope("prototype")
public class OrderPo {

    private Long orderId;
    private String orderTitle;
    private Integer orderStateId;
    private String createTime;
    private String updateTime;
    private Integer isDelete;


}
