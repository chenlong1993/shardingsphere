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

package org.apache.shardingsphere.shadow.route.engine.dml;

import org.apache.shardingsphere.infra.binder.statement.dml.UpdateStatementContext;
import org.apache.shardingsphere.shadow.api.shadow.ShadowOperationType;
import org.apache.shardingsphere.shadow.condition.ShadowColumnCondition;
import org.apache.shardingsphere.shadow.route.engine.util.ShadowExtractor;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.column.ColumnSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.expr.ExpressionSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.predicate.AndPredicate;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.predicate.WhereSegment;
import org.apache.shardingsphere.sql.parser.sql.common.util.ExpressionExtractUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Shadow update statement routing engine.
 */
public final class ShadowUpdateStatementRoutingEngine extends AbstractShadowDMLStatementRouteEngine {
    
    private final UpdateStatementContext sqlStatementContext;
    
    private final List<Object> parameters;
    
    public ShadowUpdateStatementRoutingEngine(final UpdateStatementContext sqlStatementContext, final List<Object> parameters) {
        super(sqlStatementContext, ShadowOperationType.UPDATE);
        this.sqlStatementContext = sqlStatementContext;
        this.parameters = parameters;
    }
    
    @Override
    protected Iterator<Optional<ShadowColumnCondition>> getShadowColumnConditionIterator(final String shadowColumn) {
        return new ShadowColumnConditionIterator(shadowColumn, getWhereSegment());
    }
    
    private Collection<ExpressionSegment> getWhereSegment() {
        Collection<ExpressionSegment> result = new LinkedList<>();
        for (WhereSegment each : sqlStatementContext.getWhereSegments()) {
            for (AndPredicate predicate : ExpressionExtractUtils.getAndPredicates(each.getExpr())) {
                result.addAll(predicate.getPredicates());
            }
        }
        return result;
    }
    
    private final class ShadowColumnConditionIterator extends AbstractWhereSegmentShadowColumnConditionIterator {
        
        private ShadowColumnConditionIterator(final String shadowColumn, final Collection<ExpressionSegment> predicates) {
            super(shadowColumn, predicates.iterator());
        }
        
        @Override
        protected Optional<ShadowColumnCondition> nextShadowColumnCondition(final ExpressionSegment expressionSegment, final ColumnSegment columnSegment) {
            return ShadowExtractor.extractValues(expressionSegment, parameters).map(values -> new ShadowColumnCondition(getSingleTableName(), getShadowColumn(), values));
        }
    }
}
