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

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.jfinal.plugin.activerecord.Model;

import cn.zhucongqi.excel.kit.TempFile;
import cn.zhucongqi.excel.metadata.Column;
import cn.zhucongqi.excel.metadata.Rule;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.metadata.Table;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import cn.zhucongqi.excel.write.context.GenerateContext;
import cn.zhucongqi.excel.write.context.GenerateContextImpl;

/**
 * @author Jobsz
 */
public class BuilderImpl implements Builder {

    private GenerateContext context;
    private OutputStream outputsteam;
    private Rule rule;

    public void init(OutputStream out, ExcelTypeEnum excelType) {
        //初始化时候创建临时缓存目录，用于规避POI在并发写bug
        TempFile.createPOIFilesDirectory();
        this.outputsteam = out;
        context = new GenerateContextImpl(excelType);
    }
    
    public void setRule(Rule rule) {
    	this.rule = rule;
    }

    public void addContent(List<?> data) {
        if (data != null && data.size() > 0) {
            int rowNum = context.getCurrentSheet().getLastRowNum();
            if (rowNum == 0) {
                Row row = context.getCurrentSheet().getRow(0);
                if(row == null) {
                    if (context.getHeader() == null) {
                        rowNum = -1;
                    }
                }
            }
            for (int i = 0; i < data.size(); i++) {
                int n = i + rowNum + 1;
                addOneRowOfDataToExcel(data.get(i), n);
            }
        }
    }

    public void addContent(List<?> data, Sheet sheetParam) {
    	context.buildSheet(sheetParam);
        addContent(data);
    }

    public void addContent(List<?> data, Sheet sheetParam, Table table) {
        context.buildSheet(sheetParam);
        context.buildTable(table);
        addContent(data);
    }

    public void finish() {
        try {
            context.getWorkbook().write(this.outputsteam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addOneRowOfDataToExcel(List<String> oneRowData, Row row) {
        if (oneRowData != null && oneRowData.size() > 0) {
            for (int i = 0; i < oneRowData.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(context.getCurrentContentStyle());
                cell.setCellValue(oneRowData.get(i));
            }
        }
    }

    private void addOneRowOfDataToExcel(Object oneRowData, Row row) {
        int i = 0;
        for (Column excelHeadProperty : context.getHeader().getHeaderColumns()) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(context.getCurrentContentStyle());
            String cellValue = null;
            try {
                cellValue = BeanUtils.getProperty(oneRowData, excelHeadProperty.getField().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (cellValue != null) {
                cell.setCellValue(cellValue);
            } else {
                cell.setCellValue("");
            }
            i++;
        }
    }
    
    private void addModelToExcel(Model<?> model, Row row) {
    	String attr;
    	if (null != this.rule) {
    		int cnt = this.rule.getColumnsCount();
        	Column col;
        	for (int i = 0; i < cnt; i++) {
        		col = this.rule.getColumn(i);
    			attr = col.getAttr();
				this.addCell(row, i, model.get(attr));
    		}
		} else {
			String[] attrs = model._getAttrNames();
	    	for (int i = 0; i < attrs.length; i++) {
				attr = attrs[i];
				this.addCell(row, i, model.get(attr));
			}
		}
    }
    
    private void addCell(Row row, Integer idx, Object value) {
    	Cell cell = row.createCell(idx);
        cell.setCellStyle(context.getCurrentContentStyle());
        if (null == value) {
			cell.setCellValue("");
		} else {
            cell.setCellValue(value.toString());
		}
    }

    @SuppressWarnings("unchecked")
	private void addOneRowOfDataToExcel(Object oneRowData, int n) {
        Row row = context.getCurrentSheet().createRow(n);
        if (oneRowData instanceof List) {
            addOneRowOfDataToExcel((List<String>)oneRowData, row);
        } else if (Model.class.isAssignableFrom(oneRowData.getClass())) {//jf model
        	addModelToExcel((Model<?>)oneRowData, row);
        } else{
            addOneRowOfDataToExcel(oneRowData, row);
        }
    }
}
