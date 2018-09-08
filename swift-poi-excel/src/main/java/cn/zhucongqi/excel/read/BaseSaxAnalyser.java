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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.context.AnalysisContext;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.read.event.AnalysisEventRegisterCenter;
import cn.zhucongqi.excel.read.event.OneRowAnalysisFinishEvent;
import cn.zhucongqi.excel.support.ExcelTypeEnum;

/**
 * 抽象sax模式 excel 解析类
 *
 * @author Jobsz
 */
public abstract class BaseSaxAnalyser implements AnalysisEventRegisterCenter, ExcelAnalyser {

    protected AnalysisContext analysisContext;

    private LinkedHashMap<String, AnalysisEventListener<?>> listeners = new LinkedHashMap<String, AnalysisEventListener<?>>();

    /**
     * 开始执行解析
     */
    protected abstract void execute();

    public void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom,
                     AnalysisEventListener<?> eventListener, boolean trim) {
    }

    public void appendLister(String name, AnalysisEventListener<?> listener) {
        if (!listeners.containsKey(name)) {
            listeners.put(name, listener);
        }
    }

    public void analysis(Sheet sheetParam) {
        execute();
    }

    public void analysis() {
        execute();
    }

    /**
     * 清空所有监听者
     */
    public void cleanAllListeners() {
        listeners = new LinkedHashMap<String, AnalysisEventListener<?>>();
    }

    @SuppressWarnings("unchecked")
	public void notifyListeners(OneRowAnalysisFinishEvent event) {
        analysisContext.setCurrentRowAnalysisResult(event.getData());

        //表头数据
        if (analysisContext.getCurrentRowNum() < analysisContext.getCurrentSheet().getHeadLineMun()) {
            if (analysisContext.getCurrentRowNum() <= analysisContext.getCurrentSheet().getHeadLineMun() - 1) {
                analysisContext.buildExcelHeadProperty(null,
                    (List<String>)analysisContext.getCurrentRowAnalysisResult());
            }
        } else {
            analysisContext.setCurrentRowAnalysisResult(event.getData());
            for (Map.Entry<String, AnalysisEventListener<?>> entry : listeners.entrySet()) {
                entry.getValue().invoke(analysisContext.getCurrentRowAnalysisResult(), analysisContext);
            }
        }
    }
}
