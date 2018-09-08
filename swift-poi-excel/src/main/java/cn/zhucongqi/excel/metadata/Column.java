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
import java.util.List;

public class Column implements Comparable<Column> {

	private Integer index = 99999;

	private String attr;

	private Class<?> type;

	private String dateFormat = "yyyy-MM-dd";
	
	private Field field;
	
	private List<String> head = new ArrayList<String>();

	/**
	 * Make a Column
	 * 
	 * @param attr
	 *            : attr
	 * @param type
	 *            : attr 's type
	 */
	public static Column one(String attr, Class<?> type) {
		Column column = new Column();
		column.setAttr(attr);
		column.setType(type);
		return column;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the attr
	 */
	public String getAttr() {
		return attr;
	}

	/**
	 * @param attr
	 *            the attr to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}


	/**
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * @return the head
	 */
	public List<String> getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(List<String> head) {
		this.head = head;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	public int compareTo(Column o) {
		int x = this.index;
		int y = o.getIndex();
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

	@Override
	public String toString() {
		return "Column [index=" + index + ", attr=" + attr + ", type=" + type + ", dateFormat=" + dateFormat
				+ ", field=" + field + ", head=" + head + "]";
	}
}
