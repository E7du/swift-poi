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
package cn.zhucongqi.excel.write.context;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.zhucongqi.excel.metadata.HeadProperty;
import cn.zhucongqi.excel.metadata.Table;

/**
 * @author Jobsz
 */
public interface GenerateContext {


    /**
     * 返回当前sheet
     * @return current read sheet
     */
    Sheet getCurrentSheet();

    /**
     *
     * 获取表头样式
     * @return 当前行表头样式
     */
    CellStyle getCurrentHeadCellStyle();

    /**
     * 获取内容样式
     * @return 当前行内容样式
     */
    CellStyle getCurrentContentStyle();


    /**
     * 返回WorkBook
     * @return 返回文件book
     */
    Workbook getWorkbook();

    /**
     * 构建一个sheet
     * @param sheet
     */
    void buildSheet(cn.zhucongqi.excel.metadata.Sheet sheet);

    /**
     * 构建一个Table
     * @param table
     */
    void buildTable(Table table);

    /**
     * 返回表头信息
     * @return 返回表头信息
     */
    HeadProperty getExcelHeadProperty();

    /**
     *
     * @return 是否需要表头
     */
    boolean needHead();
}


