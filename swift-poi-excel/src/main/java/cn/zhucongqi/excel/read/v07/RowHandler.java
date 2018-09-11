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
package cn.zhucongqi.excel.read.v07;

import static cn.zhucongqi.excel.consts.XmlConstants.CELL_VALUE_TAG;
import static cn.zhucongqi.excel.consts.XmlConstants.CELL_VALUE_TAG_1;
import static cn.zhucongqi.excel.consts.XmlConstants.DIMENSION;
import static cn.zhucongqi.excel.consts.XmlConstants.DIMENSION_REF;
import static cn.zhucongqi.excel.consts.XmlConstants.ROW_TAG;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.zhucongqi.excel.annotation.FieldType;
import cn.zhucongqi.excel.consts.XmlConstants;
import cn.zhucongqi.excel.kit.PositionKit;
import cn.zhucongqi.excel.read.context.AnalysisContext;
import cn.zhucongqi.excel.read.event.AnalysisEventRegisterCenter;
import cn.zhucongqi.excel.read.event.OneRowAnalysisFinishEvent;

/**
 * @author Jobsz
 */
public class RowHandler extends DefaultHandler {

    private String currentCellIndex;

    private FieldType currentCellType;

    private int curRow;

    private int curCol;

    private String[] curRowContent = new String[20];

    private String currentCellValue;

    @SuppressWarnings("unused")
	private SharedStringsTable sst;

    private AnalysisContext analysisContext;

    private AnalysisEventRegisterCenter registerCenter;

    private List<String> sharedStringList;

    public RowHandler(AnalysisEventRegisterCenter registerCenter, SharedStringsTable sst,
                      AnalysisContext analysisContext, List<String> sharedStringList) {
        this.registerCenter = registerCenter;
        this.analysisContext = analysisContext;
        this.sst = sst;
        this.sharedStringList = sharedStringList;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

        setTotalRowCount(name, attributes);

        startCell(name, attributes);

        startCellValue(name);

    }

    private void startCellValue(String name) {
        if (name.equals(CELL_VALUE_TAG) || name.equals(CELL_VALUE_TAG_1)) {
            // initialize current cell value
            currentCellValue = "";
        }
    }

    private void startCell(String name, Attributes attributes) {
        if (XmlConstants.CELL_TAG.equals(name)) {
            currentCellIndex = attributes.getValue(XmlConstants.POSITION);
            int nextRow = PositionKit.getRow(currentCellIndex);
            if (nextRow > curRow) {
                curRow = nextRow;
                // endRow(ROW_TAG);
            }
            analysisContext.setCurrentRowIdx(curRow);
            curCol = PositionKit.getCol(currentCellIndex);

            String cellType = attributes.getValue("t");
            currentCellType = FieldType.EMPTY;
            if (cellType != null && cellType.equals("s")) {
                currentCellType = FieldType.STRING;
            }
            //if ("6".equals(attributes.getValue("s"))) {
            //    // date
            //    currentCellType = FieldType.DATE;
            //}

        }
    }

    private void endCellValue(String name) throws SAXException {
        // ensure size
        if (curCol >= curRowContent.length) {
            curRowContent = Arrays.copyOf(curRowContent, (int)(curCol * 1.5));
        }
        if (CELL_VALUE_TAG.equals(name)) {

            switch (currentCellType) {
                case STRING:
                    int idx = Integer.parseInt(currentCellValue);
                    if (idx < sharedStringList.size()) {
                        currentCellValue = sharedStringList.get(idx);
                    } else {
                        currentCellValue = "";
                    }
                    currentCellType = FieldType.EMPTY;
                    break;
                //case DATE:
                //    Date dateVal = HSSFDateUtil.getJavaDate(Double.parseDouble(currentCellValue),
                //        analysisContext.use1904WindowDate());
                //    currentCellValue = TypeKit.getDefaultDateString(dateVal);
                //    currentCellType = FieldType.EMPTY;
                //    break;
                    default:
            }
            curRowContent[curCol] = currentCellValue;
        } else if (CELL_VALUE_TAG_1.equals(name)) {
            curRowContent[curCol] = currentCellValue;
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {

        endRow(name);
        endCellValue(name);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        currentCellValue += new String(ch, start, length);

    }

    private void setTotalRowCount(String name, Attributes attributes) {
        if (DIMENSION.equals(name)) {
            String d = attributes.getValue(DIMENSION_REF);
            String totalStr = d.substring(d.indexOf(":") + 1, d.length());
            String c = totalStr.toUpperCase().replaceAll("[A-Z]", "");
            analysisContext.setTotalCount(Integer.parseInt(c));
        }

    }

    private void endRow(String name) {
        if (name.equals(ROW_TAG)) {
            registerCenter.notifyListeners(new OneRowAnalysisFinishEvent(Arrays.asList(curRowContent)));
            curRowContent = new String[20];
        }
    }

}

