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
package cn.zhucongqi.excel;

import java.io.InputStream;
import java.util.List;

import com.jfinal.kit.StrKit;

import cn.zhucongqi.excel.kit.FileKit;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.ExcelAnalyser;
import cn.zhucongqi.excel.read.ExcelAnalyserImpl;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.TypeDetection;
import cn.zhucongqi.excel.support.ExcelTypeEnum;

/**
 * Excel Reader，thread unsafe
 *
 * @author Jobsz
 */
public class Reader {

    private ExcelAnalyser analyser = new ExcelAnalyserImpl();
    
    /**
     * @param fileName
     * @param customContent use {@link AnalysisEventListener<?>#invoke(Object, AnalysisContext)
     *                      }'s AnalysisContext can get this value, while read JF Model this param is model 's class.
     * @param eventListener
     */
    public Reader(String fileName, Object customContent,
                       AnalysisEventListener<?> eventListener) {
    	this.validateParam(fileName);
    	InputStream in = FileKit.getInputStream(fileName);
    	ExcelTypeEnum excelTypeEnum = TypeDetection.getExcelType(fileName);
    	validateParam(in, excelTypeEnum, eventListener);
        analyser.init(in, excelTypeEnum, customContent, eventListener, true);
    }
    
    /**
     * @param fileName  
     * @param customContent use {@link AnalysisEventListener<?>#invoke(Object, AnalysisContext)
     *                      }'s AnalysisContext can get this value
     * @param eventListener 
     * @param trim :true trim the empty String
     */
    public Reader(String fileName, Object customContent,
                       AnalysisEventListener<?> eventListener, boolean trim) {
    	this.validateParam(fileName);
    	InputStream in = FileKit.getInputStream(fileName);
    	ExcelTypeEnum excelTypeEnum = TypeDetection.getExcelType(fileName);
    	validateParam(in, excelTypeEnum, eventListener);
        analyser.init(in, excelTypeEnum, customContent, eventListener, trim);
    }

    /**
     * @param in
     * @param excelTypeEnum excel03、07
     * @param customContent use {@link AnalysisEventListener<?>#invoke(Object, AnalysisContext)
     *                      }'s AnalysisContext can get this value
     * @param eventListener
     */
    public Reader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent,
                       AnalysisEventListener<?> eventListener) {
        this(in, excelTypeEnum, customContent, eventListener, true);
    }

    /**
     * @param in
     * @param excelTypeEnum excel03、07
     * @param customContent use {@link AnalysisEventListener<?>#invoke(Object, AnalysisContext)
     *                      }'s AnalysisContext can get this value
     * @param eventListener
     * @param trim :true trim the empty String
     */
    public Reader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent,
                       AnalysisEventListener<?> eventListener, boolean trim) {
        validateParam(in, excelTypeEnum, eventListener);
        analyser.init(in, excelTypeEnum, customContent, eventListener, trim);
    }

    /**
     * read a sheet
     */
    public void read() {
        analyser.analysis();
    }

    /**
     * read the sheet
     *
     * @param sheet
     */
    public void read(Sheet sheet) {
        analyser.analysis(sheet);
    }

    /**
     * get excel's sheet[s]
     *
     * @return Sheets
     */
    public List<Sheet> getSheets() {
        return analyser.getSheets();
    }

    /**
     * close stream and delete temp files
     */
    public void finish(){
        analyser.stop();
    }

    /**
     * Validate Param
     * @param fileName
     */
    private void validateParam(String fileName) {
		if (StrKit.isBlank(fileName) 
				|| (!fileName.endsWith(ExcelTypeEnum.XLSX.getValue()) && !fileName.endsWith(ExcelTypeEnum.XLS.getValue()))) {
			throw new IllegalArgumentException("Please use the .xls or .xlsx file.");
		}
	}
    
    /**
     * Validate Param
     *
     * @param in
     * @param excelTypeEnum
     * @param eventListener
     */
    private void validateParam(InputStream in, ExcelTypeEnum excelTypeEnum, AnalysisEventListener<?> eventListener) {
        if (eventListener == null) {
            throw new IllegalArgumentException("AnalysisEventListener can not null");
        } else if (in == null) {
            throw new IllegalArgumentException("InputStream can not null");
        } else if (excelTypeEnum == null) {
            throw new IllegalArgumentException("excelTypeEnum can not null");
        }
    }
}
