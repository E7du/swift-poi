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
package cn.zhucongqi.excel.read;

import java.io.InputStream;
import java.util.List;

import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.ExcelTypeEnum;

/**
 * Excel解析器
 *
 * @author Jobsz
 */
public interface ExcelAnalyser {

    /**
     * Excel解析初始化
     *
     * @param inputStream 解析为文件流
     * @param excelTypeEnum 解析文件类型
     * @param custom 用户自定义参数用户回调时候可以获取到
     * @param eventListener 解析器需要的监听器
     * @param trim 是否去空格
     */
    void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom, AnalysisEventListener<?> eventListener,
              boolean trim);

    /**
     * 解析指定sheet,{@link AnalysisEventListener}监听中使用
     *
     * @param sheetParam 入参
     */
    void analysis(Sheet sheetParam);


    /**
     *
     * 默认解析第一个sheet，解析结果在 {@link AnalysisEventListener}监听中使用
     */
    void analysis();

    /**
     * 返回excel中包含哪些sheet
     *
     * @return 返回所有sheet
     */
    List<Sheet> getSheets();

    /**
     * 关闭流
     */
    void stop();
}
