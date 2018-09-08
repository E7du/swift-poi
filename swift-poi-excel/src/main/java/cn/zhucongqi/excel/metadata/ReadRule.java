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

import java.util.HashMap;
import java.util.Map;

public class ReadRule {
	
    private Map<Integer, Column> columns = new HashMap<Integer, Column>();

	private Map<Integer, Boolean> hasHeader = new HashMap<Integer, Boolean>();
	
	/**
	 * Get columns count
	 * @return
	 */
	public Integer getColumnsCount() {
		return this.columns.size();
	}
	
    public Map<Integer, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<Integer, Column> columns) {
        this.columns = columns;
    }
    
    public Column getColumn(Integer idx) {
    	if (this.columns.containsKey(idx)) {
			return this.columns.get(idx);
		}
    	return null;
    }
    
    public void alignColumn(Column... columns) {
    	if (null == columns) {
			return;
		}
    	for (int idx = 0; idx < columns.length; idx++) {
			Column col = columns[idx];
			col.setIndex(idx);
	    	this.columns.put(idx, col);
		}
    }
    
	public void setHasHeader(Integer sheetNo, Boolean hasHeder) {
		this.hasHeader.put(sheetNo, hasHeder);
	}
    
	public Boolean getHasHedaer(Integer sheetNo) {
		if (this.hasHeader.containsKey(sheetNo)) {
			return this.hasHeader.get(sheetNo);
		}
		return false;
	}
    
}
