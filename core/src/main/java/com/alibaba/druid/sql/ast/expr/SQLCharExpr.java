/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.sql.ast.expr;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.statement.SQLCharacterDataType;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.Collections;
import java.util.List;

public class SQLCharExpr extends SQLTextLiteralExpr implements SQLValuableExpr, Comparable<SQLCharExpr> {
    public static final SQLDataType DATA_TYPE = new SQLCharacterDataType("char");

    protected String collate;

    public SQLCharExpr() {
    }

    public SQLCharExpr(String text) {
        this.text = text;
    }

    public SQLCharExpr(String text, SQLObject parent) {
        this.text = text;
        this.parent = parent;
    }

    public String getCollate() {
        return collate;
    }

    public void setCollate(String collate) {
        this.collate = collate;
    }

    public void output(StringBuilder buf) {
        this.accept(new SQLASTOutputVisitor(buf));
    }

    protected void accept0(SQLASTVisitor visitor) {
        visitor.visit(this);
        visitor.endVisit(this);
    }

    @Override
    public Object getValue() {
        return this.text;
    }

    public String toString() {
        return SQLUtils.toSQLString(this);
    }

    public SQLCharExpr clone() {
        SQLCharExpr expr = new SQLCharExpr(this.text);
        expr.setCollate(collate);
        return expr;
    }

    public SQLDataType computeDataType() {
        return DATA_TYPE;
    }

    public List<SQLObject> getChildren() {
        return Collections.emptyList();
    }

    public SQLDateExpr toDate() {
        return new SQLDateExpr(this.text);
    }

    public SQLDateTimeExpr toDateTime() {
        return new SQLDateTimeExpr(this.text);
    }

    public SQLTimestampExpr toTimestamp() {
        String text = this.text;
        if (text.length() == 10) {
            text += " 00:00:00";
        }
        return new SQLTimestampExpr(text);
    }

    @Override
    public int compareTo(SQLCharExpr o) {
        return this.text.compareTo(o.text);
    }
}
