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

import java.io.OutputStream;
import java.util.List;

import cn.zhucongqi.excel.consts.SheetConsts;
import cn.zhucongqi.excel.metadata.Rule;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.metadata.Table;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import cn.zhucongqi.excel.write.Builder;
import cn.zhucongqi.excel.write.BuilderImpl;

/**
 * Build excel，thread unsafe
 *
 * @author Jobsz
 */
public class Writer {

    private Builder builder;

    /**
     * Build EXCEL
     *
     * @param outputStream File out stream
     * @param typeEnum Excel 03 or 07，recommend use 07.
     */
    public Writer(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        this(outputStream, typeEnum, true);
    }

    /**
     * Build EXCEL
     *
     * @param outputStream File out stream
     * @param typeEnum Excel 03 or 07，recommend use 07.
     * @param needHead build header or not.
     */
    public Writer(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        builder = new BuilderImpl();
        builder.init(outputStream, typeEnum, needHead);
    }

    /**
     * Set rule
     * @param rule
     */
    public void setRule(Rule rule) {
    	builder.setRule(rule);
    }
    
    /**
     * Write Data to Excel. <br/>
     * According to the size of data, generate multiple sheet with default style.<br/>
     * each sheet contains max data size is {@link SheetConsts#SHEET_MAX_ROW}. <br/>
     * @param data
     * @return this
     */
    public Writer write(List<?> data) {
    	if (null != data) {
    		int dataSize = data.size();
			int sheetCnt = this.getSheetCount(dataSize);
			
			for (int i = 0; i < sheetCnt; i++) {
				int start = i * SheetConsts.SHEET_MAX_ROW;
				int end = (i+1) * SheetConsts.SHEET_MAX_ROW;
				end = end > dataSize ? dataSize : end;
				List<?> tmp = data.subList(start , end);
		    	Sheet sheet = new Sheet(i);
		    	sheet.setSheetName("Sheet"+(i+1));
		        builder.addContent(tmp, sheet);
			}
		}
        return this;
    }
    
    /**
     * Write Data to Excel.
     *
     * @param data
     * @param sheet data write to sheet
     * @return this
     */
    public Writer write(List<?> data, Sheet sheet) {
        builder.addContent(data, sheet);
        return this;
    }

    /**
     * Write Data to Excel.
     *
     * @param data  List<List<String>> the List<String> contains a row data , the List<List<String>> contains many row datas.
     * @param sheet data write to sheet
     * @return this
     */
    public Writer write0(List<List<String>> data, Sheet sheet) {
        builder.addContent(data, sheet);
        return this;
    }

    /**
     * Write Data to Excel.
     *
     * @param data  List<?> a item fill to a excel row.
     * @param sheet data write to sheet
     * @param table data write to table
     * @return this
     */
    public Writer write(List<?> data, Sheet sheet, Table table) {
        builder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Write Data to Excel.
     *
     * @param data  List<List<String>> the List<String> contains a row data , the List<List<String>> contains many row datas.
     * @param sheet data write to sheet
     * @param table data write to table
     * @return this
     */
    public Writer write0(List<List<String>> data, Sheet sheet, Table table) {
        builder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Do write Action.
     */
    public void finish() {
        builder.finish();
    }
    
    private Integer getSheetCount(Integer dataSize) {
    	if (dataSize == 0) {
			return 1;
		}
    	return (int)(Math.ceil(dataSize / (SheetConsts.SHEET_MAX_ROW * 1.0)));
    }
}
