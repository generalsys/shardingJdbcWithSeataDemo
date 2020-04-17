package com.arthur.shardingsphere.sharding_demo.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Data
@Accessors(chain = true)
@Component
public class ResultEntity {

    private Integer resultCode;
    private String resultMsg;
    private Object data;
    private Map<String,Object> dataMap;

    public ResultEntity success() {
        resultCode =0;
        resultMsg = "请求成功";
        log.info("请求返回成功");
        return this;
    }

    public ResultEntity success(Object data) {
        resultCode =0;
        resultMsg = "请求成功";
        this.data =data;
        log.info("请求返回成功: "+data.toString());
        return this;
    }

    public ResultEntity success(Object data,Map<String,Object> dataMap) {
        resultCode =0;
        resultMsg = "请求成功";
        this.data =data;
        this.dataMap=dataMap;
        log.info("请求返回成功: "+data.toString());
        return this;
    }

    public ResultEntity validateFailure(String info) {
        resultCode =1;
        resultMsg = "参数验证失败："+info;
        return this;
    }

    public ResultEntity permissionFailure() {
        resultCode =1;
        resultMsg = "权限验证失败：您没有该操作的权限";
        data ="/";
        return this;
    }

    public ResultEntity microServiceFailure(String info) {
        resultCode =3;
        resultMsg = "微服务调用失败："+info;
        return this;
    }

    public ResultEntity operationFailure(String info) {
        resultCode =9;
        resultMsg = "操作失败："+info;
        return this;
    }



}
