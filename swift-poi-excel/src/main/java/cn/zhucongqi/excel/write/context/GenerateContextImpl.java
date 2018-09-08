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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import cn.zhucongqi.excel.metadata.CellRange;
import cn.zhucongqi.excel.metadata.HeadProperty;
import cn.zhucongqi.excel.metadata.Table;
import cn.zhucongqi.excel.metadata.TableStyle;
import cn.zhucongqi.excel.support.ExcelTypeEnum;

/**
 * 生成Excel上下文
 *
 * @author Jobsz
 */
public class GenerateContextImpl implements GenerateContext {

    private Sheet currentSheet;
    
    private String currentSheetName;

    private ExcelTypeEnum excelType;

    private Workbook workbook;

    private Map<Integer, Sheet> sheetMap = new ConcurrentHashMap<Integer, Sheet>();

    private Map<Integer, Table> tableMap = new ConcurrentHashMap<Integer, Table>();

    private CellStyle defaultCellStyle;

    private CellStyle currentHeadCellStyle;

    private CellStyle currentContentCellStyle;

    private HeadProperty headProperty;

    private boolean needHead = true;

    public GenerateContextImpl(ExcelTypeEnum excelType, boolean needHead) {
        if (ExcelTypeEnum.XLS.equals(excelType)) {
            this.workbook = new HSSFWorkbook();
        } else {
            this.workbook = new SXSSFWorkbook(500);
        }
        this.defaultCellStyle = buildDefaultCellStyle();
        this.needHead = needHead;
    }

    private CellStyle buildDefaultCellStyle() {
        CellStyle newCellStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)14);
        font.setBold(true);
        newCellStyle.setFont(font);
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        newCellStyle.setLocked(true);
        newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        newCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        newCellStyle.setBorderBottom(BorderStyle.THIN);
        newCellStyle.setBorderLeft(BorderStyle.THIN);
        return newCellStyle;
    }

    public void buildSheet(cn.zhucongqi.excel.metadata.Sheet sheet) {
        if (sheetMap.containsKey(sheet.getSheetNo())) {
            this.currentSheet = sheetMap.get(sheet.getSheetNo());
        } else {
            this.currentSheet = workbook.createSheet(
                sheet.getSheetName() != null ? sheet.getSheetName() : sheet.getSheetNo() + "");
            this.currentSheet.setDefaultColumnWidth(20);
            sheetMap.put(sheet.getSheetNo(), this.currentSheet);
            buildHead(sheet.getHead(), sheet.getClazz());
            buildTableStyle(sheet.getTableStyle());
            if (needHead && headProperty != null) {
                appendHeadToExcel();
            }
        }
    }

    private void buildHead(List<List<String>> head, Class<?> clazz) {
        if (head != null || clazz != null) { headProperty = new HeadProperty(clazz, head); }
    }

    public void appendHeadToExcel() {
        if (this.headProperty.getHead() != null && this.headProperty.getHead().size() > 0) {
            List<CellRange> list = this.headProperty.getCellRangeModels();
            int n = currentSheet.getLastRowNum();
            if (n > 0) {
                n = n + 4;
            }
            for (CellRange cellRangeModel : list) {
                CellRangeAddress cra = new CellRangeAddress(cellRangeModel.getFirstRow() + n,
                    cellRangeModel.getLastRow() + n,
                    cellRangeModel.getFirstCol(), cellRangeModel.getLastCol());
                currentSheet.addMergedRegion(cra);
            }
            int i = n;
            for (; i < this.headProperty.getRowNum() + n; i++) {
                Row row = currentSheet.createRow(i);
                addOneRowOfHeadDataToExcel(row, this.headProperty.getHeadByRowNum(i - n));
            }
        }
    }

    private void addOneRowOfHeadDataToExcel(Row row, List<String> headByRowNum) {
        if (headByRowNum != null && headByRowNum.size() > 0) {
            for (int i = 0; i < headByRowNum.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(this.getCurrentHeadCellStyle());
                cell.setCellValue(headByRowNum.get(i));
            }
        }
    }

    private void buildTableStyle(TableStyle tableStyle) {
        if (tableStyle != null) {
            CellStyle headStyle = buildDefaultCellStyle();
            if (tableStyle.getTableHeadFont() != null) {
                Font font = this.workbook.createFont();
                font.setFontName(tableStyle.getTableHeadFont().getFontName());
                font.setFontHeightInPoints(tableStyle.getTableHeadFont().getFontHeightInPoints());
                font.setBold(tableStyle.getTableHeadFont().isBold());
                headStyle.setFont(font);
            }
            if (tableStyle.getTableHeadBackGroundColor() != null) {
                headStyle.setFillForegroundColor(tableStyle.getTableHeadBackGroundColor().getIndex());
            }
            this.currentHeadCellStyle = headStyle;
            CellStyle contentStyle = buildDefaultCellStyle();
            if (tableStyle.getTableContentFont() != null) {
                Font font = this.workbook.createFont();
                font.setFontName(tableStyle.getTableContentFont().getFontName());
                font.setFontHeightInPoints(tableStyle.getTableContentFont().getFontHeightInPoints());
                font.setBold(tableStyle.getTableContentFont().isBold());
                contentStyle.setFont(font);
            }
            if (tableStyle.getTableContentBackGroundColor() != null) {
                contentStyle.setFillForegroundColor(tableStyle.getTableContentBackGroundColor().getIndex());
            }
            this.currentContentCellStyle = contentStyle;
        }
    }

    public void buildTable(Table table) {
        if (!tableMap.containsKey(table.getTableNo())) {
            buildHead(table.getHead(), table.getClazz());
            tableMap.put(table.getTableNo(), table);
            buildTableStyle(table.getTableStyle());
            if (needHead && headProperty != null) {
                appendHeadToExcel();
            }
        }
    }

    public HeadProperty getExcelHeadProperty() {
        return this.headProperty;
    }

    public boolean needHead() {
        return this.needHead;
    }

    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public String getCurrentSheetName() {
        return currentSheetName;
    }

    public void setCurrentSheetName(String currentSheetName) {
        this.currentSheetName = currentSheetName;
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public CellStyle getCurrentHeadCellStyle() {
        return this.currentHeadCellStyle == null ? defaultCellStyle : this.currentHeadCellStyle;
    }

    public CellStyle getCurrentContentStyle() {
        return this.currentContentCellStyle;
    }

    public Workbook getWorkbook() {
        return workbook;
    }
}
