CREATE TABLE `t_order_state` (
    `order_state_id` tinyint(3) unsigned NOT NULL COMMENT '主键',
    `order_state_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '测试标题',
    `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志',
    PRIMARY KEY (`order_state_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/**
  此处有坑：update_date中ON UPDATE CURRENT_TIMESTAMP，在整合seata AT后会导致事务回滚失败，报脏数据
  原因是:执行回滚sql时update_date的值会更新，导致回滚后与前镜像不一致。
  因此在使用seata AT后不要使用这种配置
 */
