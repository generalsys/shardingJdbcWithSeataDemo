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

import com.arthur.shardingsphere.sharding_demo.utils.CommonUtils;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @description: 取模算法 - Database精确分片策略
 *
 * @author: zhang xuan
 * @time: 2020/4/14
 */
@Slf4j
public final class ModuloPreciseShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<Long> {
    
    @Override
    public String doSharding(final Collection<String> databaseNames, final PreciseShardingValue<Long> shardingValue) {
        for (Pair<String,Integer> each : CommonUtils.nameToSuffixPair(databaseNames)) {
            if (each.getValue()==shardingValue.getValue() % databaseNames.size()) {
                return each.getKey();
            }
        }
        log.info("取模精确分片策略：没找到与分片键匹配的库名! {} : {} = {}",shardingValue.getLogicTableName(),shardingValue.getColumnName(),shardingValue.getValue());
        throw new UnsupportedOperationException();
    }
}
