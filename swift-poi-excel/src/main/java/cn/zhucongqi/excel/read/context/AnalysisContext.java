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
package cn.zhucongqi.excel.read.context;

import java.io.InputStream;
import java.util.List;

import cn.zhucongqi.excel.metadata.HeadProperty;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.ExcelTypeEnum;

/**
 * 解析文件上下文
 *
 * @author Jobsz
 */
public interface AnalysisContext {

    /**
     * 返回用户自定义数据
     *
     * @return 返回用户自定义数据
     */
    Object getCustom();

    /**
     * 返回当前Sheet
     *
     * @return current read sheet
     */
    Sheet getCurrentSheet();

    /**
     * 设置当前解析的Sheet
     *
     * @param sheet 入参
     */
    void setCurrentSheet(Sheet sheet);

    /**
     * 返回解析的Excel类型
     *
     * @return excel type
     */
    ExcelTypeEnum getExcelType();

    /**
     * 返回输入IO
     *
     * @return file io
     */
    InputStream getInputStream();

    /**
     * 获取当前监听者
     *
     * @return listener
     */
    AnalysisEventListener<?> getEventListener();

    /**
     * 获取当前行数
     *
     * @return 当前行
     */
    Integer getCurrentRowNum();

    /**
     * 设置当前行数
     *
     * @param row 设置行号
     */
    void setCurrentRowNum(Integer row);

    /**
     * 返回当前sheet共有多少行数据，仅限07版excel
     *
     * @return 总行数
     */
    @Deprecated
    Integer getTotalCount();

    /**
     * 设置总条数
     *
     * @param totalCount 总行数
     */
    void setTotalCount(Integer totalCount);

    /**
     * 返回表头信息
     *
     * @return 表头信息
     */
    HeadProperty getExcelHeadProperty();

    /**
     * 构建 HeadProperty
     *
     * @param clazz 自定义model
     * @param headOneRow 表头内容
     */
    void buildExcelHeadProperty(Class<?> clazz, List<String> headOneRow);

    /**
     * 是否trim()
     *
     * @return 是否trim
     */
    boolean trim();

    /**
     *
     * @param result 解析结果
     */
    void setCurrentRowAnalysisResult(Object result);

    /**
     *
     * @return 当前行解析结果
     */
    <T> T getCurrentRowAnalysisResult();

    /**
     * 中断
     */
    void interrupt();

    /**
     *
     * @return 是否use1904WindowDate
     */
    boolean  use1904WindowDate();

    /**
     *
     * @param use1904WindowDate  是否use1904WindowDate
     */
    void setUse1904WindowDate(boolean use1904WindowDate);
}
