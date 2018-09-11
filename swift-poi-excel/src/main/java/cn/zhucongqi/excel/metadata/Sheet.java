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

import java.util.List;

/**
 * Sheet参数
 *
 * @author Jobsz
 */
public class Sheet {

    /**
     * sheet序号 从0开始
     */
    private Integer sheetNo;

    /**
     * 名称 可不填
     */
    private String sheetName;

    private Header header;

    private TableStyle tableStyle;

    public Sheet(Integer sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Sheet(Integer sheetNo, Integer headerlineCnt) {
        this.sheetNo = sheetNo;
        this.header = new Header(headerlineCnt);
    }

    public Sheet(Integer sheetNo, Integer headLineCnt, Class<?> clazz) {
        this.sheetNo = sheetNo;
        this.header = new Header(clazz, headLineCnt);
    }

    public Sheet(Integer sheetNo, Integer headLineCnt, Class<?> clazz, String sheetName,
                 List<List<String>> headerTitles) {
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
        this.header = new Header(clazz, headLineCnt);
        this.header.setHeaderTitles(headerTitles);
    }
    
    public int getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}
    
	public Boolean hasHeader() {
		return this.header != null;
	}
	
	public void setHeaderLineCnt(Integer headerLineCnt) {
		this.header.setHeadLineCnt(headerLineCnt);
	}
	
	public Integer getHeaderLineCnt() {
		if (!this.hasHeader()) {
			return 0;
		}
		return this.header.getHeadLineCnt();
	}
	
	public void setClazz(Class<?> clazz) {
		this.header.setHeaderClazz(clazz);
		if (this.header.getHeaderLineCnt() == 0) {
			this.header.setHeaderLineCnt(1);
		}
	}
	
	public Class<?> getClazz() {
		if (!this.hasHeader()) {
			return null;
		}
		return this.header.getHeaderClazz();
	}
}
