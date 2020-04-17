package com.arthur.shardingsphere.sharding_demo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: arthur
 * @time: 2020/4/6 20:12
 */
@Data
@Accessors(chain = true)
@Component
@Scope("prototype")
public class OrderStatePo {

    private Integer orderStateId;
    private String orderStateTitle;
    private String createDate;
    private String updateDate;
    private Integer isDelete;
}
