/*
 * Copyright 2018 Jobsz (zcq@zhucongqi.cn)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
*/
package cn.zhucongqi.excel.metadata;

import java.util.List;

/**
 * @author Jobsz
 */
public class Table {
    /**
     * 对用的表头模型
     */
    private Class<?> clazz;

    /**
     * 对用的表头层级树,用于clazz不确定时候，动态生成表头
     */
    private List<List<String>> headerTitles;

    /**
     * 第几个table,用于和其他table区分
     */
    private Integer tableNo;

    /**
     * 支持表格简单样式自定义
     */
    private TableStyle tableStyle;

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    public Table(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<List<String>> getHead() {
        return headerTitles;
    }

    public void setHead(List<List<String>> head) {
        this.headerTitles = head;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }
    
    public Boolean isNull() {
    	return this.clazz == null || this.headerTitles == null;
    }
}
