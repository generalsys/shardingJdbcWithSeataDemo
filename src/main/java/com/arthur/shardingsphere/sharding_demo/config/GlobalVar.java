package com.arthur.shardingsphere.sharding_demo.config;

/**
 * @description: 全局变量
 *
 * @author: zhang xuan
 * @time: 2020/4/14
 */
public class GlobalVar {

    //数据源个数 取模分片算法使用
    public static Integer MODULO_DS_MASTER_SIZE =3;

    //每表的最大数据量 范围分片算法使用
    public static Integer RANGE_TABLE_RECORDS_SIZE=5000000;
    //每数据源的最大表数量 范围分片算法使用
    public static Integer RANGE_TABLE_SIZE=10;
}
