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

package org.apache.shardingsphere.data.pipeline.core.job;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Job status.
 */
@RequiredArgsConstructor
@Getter
public enum JobStatus {
    
    /**
     * Job is in running status.
     */
    RUNNING(true),
    
    /**
     * Job is in prepare status.
     */
    PREPARING(true),
    
    /**
     * Job is in execute inventory task status.
     */
    EXECUTE_INVENTORY_TASK(true),
    
    /**
     * Job is in execute incremental task status.
     */
    EXECUTE_INCREMENTAL_TASK(true),
    
    /**
     * Job is finished.
     */
    FINISHED(false),
    
    /**
     * Consistency check job execute failed.
     */
    CONSISTENCY_CHECK_FAILURE(false);
    
    private final boolean running;
}
