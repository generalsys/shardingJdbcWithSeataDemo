package com.arthur.shardingsphere.sharding_demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlProvider {

    private boolean isUnderLined=true;
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    public static final String BLANK_VALUE = "*&^%$!";

    public String insertSql(Object data) {
        if (Objects.isNull(data)) {
            return null;
        }

        try {
            return new SQL() {
                {
                    INSERT_INTO(getTableName(data));
                    Field[] fieldList = data.getClass().getDeclaredFields();
                    for (Field field:fieldList) {
                        // 获取属性的名字
                        String name = field.getName();
                        // 将属性的首字符大写，方便构造get，set方法
                        String nameUp = name.substring(0, 1).toUpperCase() + name.substring(1);
                        Object value= data.getClass().getMethod("get" + nameUp).invoke(data);
                        if(Objects.isNull(value) || StringUtils.isBlank(value.toString())) {
                            continue;
                        }
                        if(value.toString().equals(BLANK_VALUE)) {
                            data.getClass().getMethod("set" + nameUp,String.class).invoke(data,"");
                        }
                        VALUES(isUnderLined?humpToLine(name):name,"#{"+name+"}");
                    }
                }
            }.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String updateSql(String key, Object data) {
        if (Objects.isNull(data) || StringUtils.isBlank(key)) {
            return null;
        }

        try {
            return new SQL() {
                {
                    UPDATE(getTableName(data));
                    Field[] fieldList = data.getClass().getDeclaredFields();
                    for (Field field:fieldList) {
                        // 获取属性的名字
                        String name = field.getName();
                        // 将属性的首字符大写，方便构造get，set方法
                        String nameUp = name.substring(0, 1).toUpperCase() + name.substring(1);
                        Object value= data.getClass().getMethod("get" + nameUp).invoke(data);

                        //如果是空值则跳过
                        if(value==null || value.toString().isEmpty()) {
                            continue;
                        }
                        if(value.toString().equals(BLANK_VALUE)) {
                            data.getClass().getMethod("set" + nameUp,String.class).invoke(data,"");
                        }
                        if(name.equals(key)) {
                            continue;
                        }

                        SET((isUnderLined?humpToLine(name):name) + " = #{param2."+name+"}");
                    }
                    WHERE((isUnderLined?humpToLine(key):key) +"=#{param2."+key+"}");
                }
            }.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteSql(String tableName, String keyName, String keyValue) {
        return new SQL() {
            {
                DELETE_FROM(tableName);
                WHERE((isUnderLined?humpToLine(keyName):keyName) + " = #{"+keyValue+"}");
            }
        }.toString();
    }

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        if(StringUtils.isBlank(str)) {
            return null;
        }

        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine(String str) {
        if(StringUtils.isBlank(str)) {
            return null;
        }

        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String getTableName(Object o) {
        String className=o.getClass().getSimpleName();
        String tableName = className.substring(0, 1).toLowerCase() + className.substring(1);
        return "t_".concat(humpToLine(tableName).replace("_po",""));
    }

}
