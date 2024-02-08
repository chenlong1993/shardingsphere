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

package org.apache.shardingsphere.data.pipeline.migration.distsql.handler.query;

import org.apache.shardingsphere.data.pipeline.core.consistencycheck.table.TableDataConsistencyChecker;
import org.apache.shardingsphere.data.pipeline.migration.distsql.statement.ShowMigrationCheckAlgorithmsStatement;
import org.apache.shardingsphere.distsql.handler.engine.query.DistSQLQueryExecutor;
import org.apache.shardingsphere.distsql.handler.engine.query.ral.plugin.PluginMetaDataQueryResultRows;
import org.apache.shardingsphere.infra.merge.result.impl.local.LocalDataQueryResultRow;
import org.apache.shardingsphere.mode.manager.ContextManager;

import java.util.Collection;

/**
 * Show migration check algorithms' executor.
 */
public final class ShowMigrationCheckAlgorithmsExecutor implements DistSQLQueryExecutor<ShowMigrationCheckAlgorithmsStatement> {
    
    private final PluginMetaDataQueryResultRows pluginMetaDataQueryResultRows = new PluginMetaDataQueryResultRows(TableDataConsistencyChecker.class);
    
    @Override
    public Collection<LocalDataQueryResultRow> getRows(final ShowMigrationCheckAlgorithmsStatement sqlStatement, final ContextManager contextManager) {
        return pluginMetaDataQueryResultRows.getRows();
    }
    
    @Override
    public Collection<String> getColumnNames() {
        return pluginMetaDataQueryResultRows.getColumnNames();
    }
    
    @Override
    public Class<ShowMigrationCheckAlgorithmsStatement> getType() {
        return ShowMigrationCheckAlgorithmsStatement.class;
    }
}
