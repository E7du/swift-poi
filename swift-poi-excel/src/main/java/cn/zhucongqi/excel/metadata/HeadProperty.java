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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zhucongqi.excel.annotation.ColumnIndex;
import cn.zhucongqi.excel.annotation.Property;

/**
 * 表头信息
 *
 * @author Jobsz
 */
public class HeadProperty {

    /**
     * 表头数据对应的Class
     */
    private Class<?> headClazz;

    /**
     * 表头名称
     */
    private List<List<String>> head = new ArrayList<List<String>>();

    /**
     * Excel每列表头数据
     */
    private List<Column> columns = new ArrayList<Column>();

    /**
     * key:Excel列号，value:表头数据
     */
    private Map<Integer, Column> columnMap = new HashMap<Integer, Column>();

    public HeadProperty(Class<?> headClazz, List<List<String>> head) {
        this.headClazz = headClazz;
        this.head = head;
        initColumns();
    }

    /**
     * 初始化每列
     */
    private void initColumns() {
        if (this.headClazz != null) {
            Field[] fields = this.headClazz.getDeclaredFields();
            List<List<String>> headList = new ArrayList<List<String>>();
            for (Field f : fields) {
            	initOneColumn(f);
            }
            //对列排序
            Collections.sort(columns);
            if (head == null || head.size() == 0) {
                for (Column Column : columns) {
                    headList.add(Column.getHead());
                }
                this.head = headList;
            }
        }
    }
    
    /**
     * 初始化一列
     *
     * @param f
     */
    private void initOneColumn(Field f) {
        Property p = f.getAnnotation(Property.class);
        Column header = null;
        if (p != null) {
            header = new Column();
            header.setField(f);
            header.setHead(Arrays.asList(p.value()));
            header.setIndex(p.index());
            header.setDateFormat(p.format());
            columnMap.put(p.index(), header);
        } else {
            ColumnIndex columnNum = f.getAnnotation(ColumnIndex.class);
            if (columnNum != null) {
                header = new Column();
                header.setField(f);
                header.setIndex(columnNum.value());
                header.setDateFormat(columnNum.format());
                columnMap.put(columnNum.value(), header);
            }
        }
        if (header != null) {
            this.columns.add(header);
        }

    }

    /**
     * 将表头的一行数据，转换为一列一列形式，组成表头
     *
     * @param row 表头中的一行数据
     */
    public void appendOneRow(List<String> row) {

        for (int i = 0; i < row.size(); i++) {
            List<String> list;
            if (head.size() <= i) {
                list = new ArrayList<String>();
                head.add(list);
            } else {
                list = head.get(0);
            }
            list.add(row.get(i));
        }

    }

    /**
     * 根据Excel中的列号，获取Excel的表头信息
     *
     * @param columnNum 列号
     * @return Column
     */
    public Column getExcelColumnProperty(int columnNum) {
        Column Column = columnMap.get(columnNum);
        if (Column == null) {
            if (head != null && head.size() > columnNum) {
                List<String> columnHead = head.get(columnNum);
                for (Column colProperty : columns) {
                    if (headEquals(columnHead, colProperty.getHead())) {
                        return colProperty;
                    }
                }
            }
        }
        return Column;
    }

    /**
     * 根据Excel中的列号，获取Excel的表头信息
     *
     * @param columnNum 列号
     * @return Column
     */
    public Column getExcelColumnProperty1(int columnNum) {
        return columnMap.get(columnNum);

    }

    /**
     * 判断表头是否相同
     *
     * @param columnHead
     * @param head
     * @return
     */
    private boolean headEquals(List<String> columnHead, List<String> head) {
        boolean result = true;
        if (columnHead == null || head == null || columnHead.size() != head.size()) {
            return false;
        } else {
            for (int i = 0; i < head.size(); i++) {
                if (!head.get(i).equals(columnHead.get(i))) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public Class<?> getHeadClazz() {
        return headClazz;
    }

    public void setHeadClazz(Class<?> headClazz) {
        this.headClazz = headClazz;
    }

    public List<List<String>> getHead() {
        return this.head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<CellRange> getCellRangeModels() {
        List<CellRange> rangs = new ArrayList<CellRange>();
        for (int i = 0; i < head.size(); i++) {
            List<String> columnvalues = head.get(i);
            for (int j = 0; j < columnvalues.size(); j++) {
                int lastRow = getLastRangRow(j, columnvalues.get(j), columnvalues);
                int lastColumn = getLastRangColumn(columnvalues.get(j), getHeadByRowNum(j), i);
                if (lastRow >= 0 && lastColumn >= 0 && (lastRow > j || lastColumn > i)) {
                    rangs.add(new CellRange(j, lastRow, i, lastColumn));
                }

            }
        }
        return rangs;
    }

    public List<String> getHeadByRowNum(int rowNum) {
        List<String> l = new ArrayList<String>(head.size());
        for (List<String> list : head) {
            if (list.size() > rowNum) {
                l.add(list.get(rowNum));
            } else {
                l.add(list.get(list.size() - 1));
            }
        }
        return l;
    }

    /**
     * @param value
     * @param headByRowNum
     * @param i
     * @return
     */
    private int getLastRangColumn(String value, List<String> headByRowNum, int i) {
        if (headByRowNum.indexOf(value) < i) {
            return -1;
        } else {
            return headByRowNum.lastIndexOf(value);
        }
    }

    private int getLastRangRow(int j, String value, List<String> columnvalue) {

        if (columnvalue.indexOf(value) < j) {
            return -1;
        }
        if (value != null && value.equals(columnvalue.get(columnvalue.size() - 1))) {
            return getRowNum() - 1;
        } else {
            return columnvalue.lastIndexOf(value);
        }
    }

    public int getRowNum() {
        int headRowNum = 0;
        for (List<String> list : head) {
            if (list != null && list.size() > 0) {
                if (list.size() > headRowNum) {
                    headRowNum = list.size();
                }
            }
        }
        return headRowNum;
    }

}
