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
package cn.zhucongqi.excel.write;

import java.io.OutputStream;
import java.util.List;

import cn.zhucongqi.excel.metadata.Rule;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.metadata.Table;
import cn.zhucongqi.excel.support.ExcelTypeEnum;

/**
 * Excel构建器
 *
 * @author Jobsz
 */
public interface Builder {

    /**
     * 初始化Excel构造器
     *
     * @param out       文件输出流
     * @param excelType 输出Excel类型，建议使用07版xlsx（性能，内存消耗，cpu使用都远低于03版xls）
     * @param needHead  是否需要将表头写入Excel
     */
    void init(OutputStream out, ExcelTypeEnum excelType, boolean needHead);
    
    /**
     * Set Rule
     * @param rule
     */
    void setRule(Rule rule);

    /**
     * 向Excel增加的内容
     *
     * @param data 数据格式
     */
    void addContent(List<?> data);

    /**
     * 向Excel增加的内容
     *
     * @param data       数据格式
     * @param sheetParam 数据写到某个sheet中
     */
    void addContent(List<?> data, Sheet sheetParam);

    /**
     * 向Excel增加的内容
     *
     * @param data       数据格式
     * @param sheetParam 数据写到某个sheet中
     * @param table      写到某个sheet的某个Table
     */
    void addContent(List<?> data, Sheet sheetParam, Table table);

    /**
     * 关闭资源
     */
    void finish();
}
