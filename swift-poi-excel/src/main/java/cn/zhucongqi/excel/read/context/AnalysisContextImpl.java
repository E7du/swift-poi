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
import java.util.ArrayList;
import java.util.List;

import cn.zhucongqi.excel.metadata.Header;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.read.exception.AnalysisException;
import cn.zhucongqi.excel.support.ExcelTypeEnum;

/**
 * 解析Excel上线文默认实现
 *
 * @author Jobsz
 */
public class AnalysisContextImpl implements AnalysisContext {

    private Object custom;

    private Sheet currentSheet;

    private ExcelTypeEnum excelType;

    private InputStream inputStream;

    private AnalysisEventListener<?> eventListener;

    private Integer currentRowIdx;

    private Integer totalCount;

    private Header header;

    private boolean trim;

    private boolean use1904WindowDate = false;

    public void setUse1904WindowDate(boolean use1904WindowDate) {
        this.use1904WindowDate = use1904WindowDate;
    }

    @SuppressWarnings("unchecked")
	public <T> T getCurrentRowAnalysisResult() {
        return (T)currentRowAnalysisResult;
    }

    public void interrupt() {
        throw new AnalysisException("interrupt error");
    }

    public boolean use1904WindowDate() {
        return use1904WindowDate;
    }

    public void setCurrentRowAnalysisResult(Object currentRowAnalysisResult) {
        this.currentRowAnalysisResult = currentRowAnalysisResult;
    }

    private Object currentRowAnalysisResult;

    public AnalysisContextImpl(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom,
                               AnalysisEventListener<?> listener, boolean trim) {
        this.custom = custom;
        this.eventListener = listener;
        this.inputStream = inputStream;
        this.excelType = excelTypeEnum;
        this.trim = trim;
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
        if (currentSheet.getClazz() != null) {
            buildHeader(currentSheet.getClazz(), null);
        }
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public Object getCustom() {
        return custom;
    }

    public void setCustom(Object custom) {
        this.custom = custom;
    }

    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public AnalysisEventListener<?> getEventListener() {
        return eventListener;
    }

    public void setEventListener(AnalysisEventListener<?> eventListener) {
        this.eventListener = eventListener;
    }

    public Integer getCurrentRowIdx() {
        return this.currentRowIdx;
    }

    public void setCurrentRowIdx(Integer row) {
        this.currentRowIdx = row;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Header getHeader() {
        return this.header;
    }

    public void buildHeader(Class<?> clazz, List<String> headOneRow) {
        if (this.header == null && (clazz != null || headOneRow != null)) {
            this.header = new Header(clazz, new ArrayList<List<String>>());
        }
        if (this.header.getHeaderTitles() == null && headOneRow != null) {
            this.header.appendOneHeaderRow(headOneRow);
        }
    }

    public boolean trim() {
        return this.trim;
    }
}
