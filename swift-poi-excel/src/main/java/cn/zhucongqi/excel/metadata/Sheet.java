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
 * Sheet参数
 *
 * @author Jobsz
 */
public class Sheet {

    /**
     * 表头行数
     */
    private int headLineCnt;

    /**
     * sheet序号 从0开始
     */
    private int sheetNo;

    /**
     * 名称 可不填
     */
    private String sheetName;

    /**
     * 对用的表头模型
     */
    private Class<?> clazz;

    /**
     * 对用的表头层级树,用于clazz不确定时候，动态生成表头
     */
    private List<List<String>> head;

    /**
     *
     */
    private TableStyle tableStyle;

    public Sheet(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Sheet(int sheetNo, int headLineMun) {
        this.sheetNo = sheetNo;
        this.headLineCnt = headLineMun;
    }

    public Sheet(int sheetNo, int headLineMun, Class<?> clazz) {
        this.sheetNo = sheetNo;
        this.headLineCnt = headLineMun;
        this.clazz = clazz;
    }

    public Sheet(int sheetNo, int headLineMun, Class<?> clazz, String sheetName,
                 List<List<String>> head) {
        this.sheetNo = sheetNo;
        this.clazz = clazz;
        this.headLineCnt = headLineMun;
        this.sheetName = sheetName;
        this.head = head;
    }
    
    public Boolean hashHead() {
    	return this.clazz != null && this.head != null && this.headLineCnt != 0;
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
        if (headLineCnt == 0) {
            this.headLineCnt = 1;
        }
    }

    public int getHeadLineMun() {
        return headLineCnt;
    }

    public void setHeadLineMun(int headLineMun) {
        this.headLineCnt = headLineMun;
    }

    public int getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    @Override
    public String toString() {
        return "Sheet{" +
            "headLineCnt=" + headLineCnt +
            ", sheetNo=" + sheetNo +
            ", sheetName='" + sheetName + '\'' +
            ", clazz=" + clazz +
            ", head=" + head +
            ", tableStyle=" + tableStyle +
            '}';
    }
}
