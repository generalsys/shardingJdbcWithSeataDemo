/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthur.shardingsphere.sharding_demo.algorithm;

import com.arthur.shardingsphere.sharding_demo.config.GlobalVar;
import com.arthur.shardingsphere.sharding_demo.utils.CommonUtils;
import com.google.common.collect.BoundType;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @description: 范围算法 - Database范围分片策略
 *
 * @author: zhang xuan
 * @time: 2020/4/16
 */
@Slf4j
public final class RangeRangeShardingTableAlgorithm implements RangeShardingAlgorithm<Long> {
    
    @Override
    public Collection<String> doSharding(final Collection<String> tableNames, final RangeShardingValue<Long> shardingValueRange) {
        Set<String> result = new LinkedHashSet<>();
        //计算分片键落在表的范围
        Integer rangeDsRecordsSize = GlobalVar.RANGE_TABLE_RECORDS_SIZE * GlobalVar.RANGE_TABLE_SIZE;
        Long lowerBound=null,upperBound=null;
        if (shardingValueRange.getValueRange().hasLowerBound()) {
            if (shardingValueRange.getValueRange().lowerBoundType().equals(BoundType.CLOSED)) {
                lowerBound = shardingValueRange.getValueRange().lowerEndpoint() % rangeDsRecordsSize / GlobalVar.RANGE_TABLE_RECORDS_SIZE;
            }else {
                lowerBound = (shardingValueRange.getValueRange().lowerEndpoint() + 1) % rangeDsRecordsSize / GlobalVar.RANGE_TABLE_RECORDS_SIZE;
            }
        }
        if (shardingValueRange.getValueRange().hasUpperBound()) {
            if (shardingValueRange.getValueRange().upperBoundType().equals(BoundType.CLOSED)) {
                upperBound = shardingValueRange.getValueRange().upperEndpoint() % rangeDsRecordsSize / GlobalVar.RANGE_TABLE_RECORDS_SIZE;
            }else {
                upperBound = (shardingValueRange.getValueRange().upperEndpoint() - 1) % rangeDsRecordsSize / GlobalVar.RANGE_TABLE_RECORDS_SIZE;
            }
        }
        //匹配的表返回结果
        for (Pair<String,Integer> each : CommonUtils.nameToSuffixPair(tableNames)) {
            if (lowerBound != null) {
                if (upperBound != null) {
                    if (each.getValue() >= lowerBound && each.getValue() <= upperBound) {
                        result.add(each.getKey());
                    }
                }else {
                    if (each.getValue() >= lowerBound) {
                        result.add(each.getKey());
                    }
                }
            } else if (upperBound != null) {
                if (each.getValue() <= upperBound) {
                    result.add(each.getKey());
                }
            }
        }

        if (CollectionUtils.isEmpty(result)) {
            log.info("范围range分片策略：没找到与分片键匹配的库名! {} : {} in {}",shardingValueRange.getLogicTableName(),
                    shardingValueRange.getColumnName(),shardingValueRange.getValueRange().toString());
            throw new UnsupportedOperationException();
        }

        return result;
    }
}
