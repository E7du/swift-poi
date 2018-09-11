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
package cn.zhucongqi.excel.metadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zhucongqi.excel.annotation.ColumnIndex;
import cn.zhucongqi.excel.annotation.Property;

public class Header {

	 /**
     * 表头行数
     */
	private Integer headerLineCnt;

	/**
	 * 表头数据对应的Class
	 */
	private Class<?> headerClazz;

	/**
	 * 表头名称
	 */
	private List<List<String>> headerTitles = new ArrayList<List<String>>();

	/**
	 * Excel每列表头数据
	 */
	private List<Column> headerColumns = new ArrayList<Column>();

	/**
	 * key:Excel列号，value:表头数据
	 */
	private Map<Integer, Column> headerColumnMap = new HashMap<Integer, Column>();
	
	public Header(Integer headerlineCnt) {
		this.headerLineCnt = headerlineCnt;
	}
	
	public Header(Class<?> headClazz, Integer headerLineCnt) {
		this.headerClazz = headClazz;
		this.setHeadLineCnt(headerLineCnt);
	}
	
	public Header(Class<?> headClazz, List<List<String>> headerTitles) {
		this.headerClazz = headClazz;
		this.headerTitles = headerTitles;
		initHeaderColumns();
	}

	/**
	 * 初始化每列
	 */
	private void initHeaderColumns() {
		if (this.headerClazz != null) {
			Field[] fields = this.headerClazz.getDeclaredFields();
			for (Field f : fields) {
				initOneHeaderColumn(f);
			}
			// 对列排序
			Collections.sort(headerColumns);
			
			List<List<String>> headerTitles = new ArrayList<List<String>>();
			if (!this.hasHeaderTitles()) {
				for (Column col : headerColumns) {
					headerTitles.add(col.getHead());
				}
				this.headerTitles = headerTitles;
			}
		}
	}

	/**
	 * 初始化一列
	 *
	 * @param f
	 */
	private void initOneHeaderColumn(Field f) {
		Property p = f.getAnnotation(Property.class);
		Column headerCol = null;
		if (p != null) {
			headerCol = new Column();
			headerCol.setField(f);
			headerCol.setHead(Arrays.asList(p.value()));
			headerCol.setIndex(p.index());
			headerCol.setDateFormat(p.format());
			headerColumnMap.put(p.index(), headerCol);
		} else {
			ColumnIndex columnNum = f.getAnnotation(ColumnIndex.class);
			if (columnNum != null) {
				headerCol = new Column();
				headerCol.setField(f);
				headerCol.setIndex(columnNum.value());
				headerCol.setDateFormat(columnNum.format());
				headerColumnMap.put(columnNum.value(), headerCol);
			}
		}
		if (headerCol != null) {
			this.headerColumns.add(headerCol);
		}
	}

	/**
	 * 将表头的一行数据，转换为一列一列形式，组成表头
	 *
	 * @param row
	 *            表头中的一行数据
	 */
	public void appendOneHeaderRow(List<String> row) {
		for (int i = 0; i < row.size(); i++) {
			List<String> oneHeader;
			if (headerTitles.size() <= i) {
				oneHeader = new ArrayList<String>();
				headerTitles.add(oneHeader);
			} else {
				oneHeader = headerTitles.get(0);
			}
			oneHeader.add(row.get(i));
		}
	}

	/**
	 * 根据Excel中的列号，获取Excel的表头信息
	 *
	 * @param columnIdx
	 *            列号
	 * @return Column
	 */
	public Column getHeaderColumnByIdx(Integer columnIdx) {
		Column Column = headerColumnMap.get(columnIdx);
		if (Column == null) {
			if (headerTitles != null && headerTitles.size() > columnIdx) {
				List<String> columnHead = headerTitles.get(columnIdx);
				for (Column col : headerColumns) {
					if (headerEquals(columnHead, col.getHead())) {
						return col;
					}
				}
			}
		}
		return Column;
	}

	/**
	 * 根据Excel中的列号，获取Excel的表头信息
	 *
	 * @param columnIdx
	 *            列号
	 * @return Column
	 */
	public Column getHeaderColumnByIdx1(Integer columnIdx) {
		return headerColumnMap.get(columnIdx);
	}

	/**
	 * 判断表头是否相同
	 *
	 * @param columnHeader
	 * @param header
	 * @return
	 */
	private boolean headerEquals(List<String> columnHeader, List<String> header) {
		boolean result = true;
		if (columnHeader == null || header == null || columnHeader.size() != header.size()) {
			return false;
		} else {
			for (int i = 0; i < header.size(); i++) {
				if (!header.get(i).equals(columnHeader.get(i))) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	public List<CellRange> getCellRangeModels() {
		List<CellRange> rangs = new ArrayList<CellRange>();
		for (int i = 0; i < headerTitles.size(); i++) {
			List<String> columnvalues = headerTitles.get(i);
			for (int j = 0; j < columnvalues.size(); j++) {
				int lastRow = getLastRangRow(j, columnvalues.get(j), columnvalues);
				int lastColumn = getLastRangColumn(columnvalues.get(j), getHeadByRowNum(j), i);
				if (lastRow >= 0 && lastColumn >= 0 && (lastRow > j || lastColumn > i)) {
					rangs.add(new CellRange(j, lastRow, i, lastColumn));
				}

			}
		}
		return rangs;
	}

	public List<String> getHeadByRowNum(int rowNum) {
		List<String> l = new ArrayList<String>(headerTitles.size());
		for (List<String> list : headerTitles) {
			if (list.size() > rowNum) {
				l.add(list.get(rowNum));
			} else {
				l.add(list.get(list.size() - 1));
			}
		}
		return l;
	}

	/**
	 * @param value
	 * @param headByRowNum
	 * @param i
	 * @return
	 */
	private int getLastRangColumn(String value, List<String> headByRowNum, int i) {
		if (headByRowNum.indexOf(value) < i) {
			return -1;
		} else {
			return headByRowNum.lastIndexOf(value);
		}
	}

	private int getLastRangRow(int j, String value, List<String> columnvalue) {

		if (columnvalue.indexOf(value) < j) {
			return -1;
		}
		if (value != null && value.equals(columnvalue.get(columnvalue.size() - 1))) {
			return getRowNum() - 1;
		} else {
			return columnvalue.lastIndexOf(value);
		}
	}

	public int getRowNum() {
		int headRowNum = 0;
		for (List<String> list : headerTitles) {
			if (list != null && list.size() > 0) {
				if (list.size() > headRowNum) {
					headRowNum = list.size();
				}
			}
		}
		return headRowNum;
	}
	
	public Boolean hasHeaderTitles() {
		return this.headerTitles != null && this.headerTitles.size() > 0;
	}

	/**
	 * @return the headLineCnt
	 */
	public Integer getHeadLineCnt() {
		return headerLineCnt;
	}

	/**
	 * @param headLineCnt the headLineCnt to set
	 */
	public void setHeadLineCnt(Integer headLineCnt) {
		this.headerLineCnt = headLineCnt;
	}

	/**
	 * @return the headerLineCnt
	 */
	public Integer getHeaderLineCnt() {
		return headerLineCnt;
	}

	/**
	 * @param headerLineCnt the headerLineCnt to set
	 */
	public void setHeaderLineCnt(Integer headerLineCnt) {
		this.headerLineCnt = headerLineCnt;
	}

	/**
	 * @return the headerClazz
	 */
	public Class<?> getHeaderClazz() {
		return headerClazz;
	}

	/**
	 * @param headerClazz the headerClazz to set
	 */
	public void setHeaderClazz(Class<?> headerClazz) {
		this.headerClazz = headerClazz;
	}

	/**
	 * @return the headerTitles
	 */
	public List<List<String>> getHeaderTitles() {
		return headerTitles;
	}

	/**
	 * @param headerTitles the headerTitles to set
	 */
	public void setHeaderTitles(List<List<String>> headerTitles) {
		this.headerTitles = headerTitles;
	}

	/**
	 * @return the headerColumns
	 */
	public List<Column> getHeaderColumns() {
		return headerColumns;
	}

	/**
	 * @param headerColumns the headerColumns to set
	 */
	public void setHeaderColumns(List<Column> headerColumns) {
		this.headerColumns = headerColumns;
	}

	/**
	 * @return the headerColumnMap
	 */
	public Map<Integer, Column> getHeaderColumnMap() {
		return headerColumnMap;
	}

	/**
	 * @param headerColumnMap the headerColumnMap to set
	 */
	public void setHeaderColumnMap(Map<Integer, Column> headerColumnMap) {
		this.headerColumnMap = headerColumnMap;
	}
}