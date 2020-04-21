CREATE TABLE `t_order_item_0` (
    `order_item_id` bigint(18) unsigned NOT NULL COMMENT '主键',
    `order_item_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '测试标题',
    `order_id` bigint(18) unsigned NOT NULL COMMENT 'orderId',
    `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
    `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志',
    PRIMARY KEY (`order_item_id`) USING BTREE,
    KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `t_order_item_1` (
    `order_item_id` bigint(18) unsigned NOT NULL COMMENT '主键',
    `order_item_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '测试标题',
    `order_id` bigint(18) unsigned NOT NULL COMMENT 'orderId',
    `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
    `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志',
    PRIMARY KEY (`order_item_id`) USING BTREE,
    KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;