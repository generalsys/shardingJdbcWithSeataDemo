package com.arthur.shardingsphere.sharding_demo.utils;

import javafx.util.Pair;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zhang xuan
 * @time: 2020/4/16
 */
public class CommonUtils {

    /**
     * @description: 数据库名/表名 String集合转换Pair集合，key=String，value=后缀数字
     * @param target
     * @return: java.util.Collection<javafx.util.Pair<java.lang.String,java.lang.Integer>>
     */
    public static Collection<Pair<String, Integer>> nameToSuffixPair(Collection<String> target) {
        return target.stream().map(e -> new Pair<>(e, Integer.parseInt(e.substring(e.lastIndexOf("_")+1)))).collect(Collectors.toList());
    }
}
