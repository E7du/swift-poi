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

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author Jobsz
 */
public class TableStyle {

    /**
     * 表头背景颜色
     */
    private IndexedColors tableHeadBackGroundColor;

    /**
     * 表头字体样式
     */
    private Font tableHeadFont;

    /**
     * 表格内容字体样式
     */
    private Font tableContentFont;

    /**
     * 表格内容背景颜色
     */
    private IndexedColors tableContentBackGroundColor;

    public IndexedColors getTableHeadBackGroundColor() {
        return tableHeadBackGroundColor;
    }

    public void setTableHeadBackGroundColor(IndexedColors tableHeadBackGroundColor) {
        this.tableHeadBackGroundColor = tableHeadBackGroundColor;
    }

    public Font getTableHeadFont() {
        return tableHeadFont;
    }

    public void setTableHeadFont(Font tableHeadFont) {
        this.tableHeadFont = tableHeadFont;
    }

    public Font getTableContentFont() {
        return tableContentFont;
    }

    public void setTableContentFont(Font tableContentFont) {
        this.tableContentFont = tableContentFont;
    }

    public IndexedColors getTableContentBackGroundColor() {
        return tableContentBackGroundColor;
    }

    public void setTableContentBackGroundColor(IndexedColors tableContentBackGroundColor) {
        this.tableContentBackGroundColor = tableContentBackGroundColor;
    }
}
