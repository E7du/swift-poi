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

/**
 * @author Jobsz
 */
public class CellRange {

    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;

    public CellRange(int firstRow, int lastRow, int firstCol, int lastCol) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }

	/**
	 * @return the firstRow
	 */
	public int getFirstRow() {
		return firstRow;
	}

	/**
	 * @param firstRow the firstRow to set
	 */
	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	/**
	 * @return the lastRow
	 */
	public int getLastRow() {
		return lastRow;
	}

	/**
	 * @param lastRow the lastRow to set
	 */
	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	/**
	 * @return the firstCol
	 */
	public int getFirstCol() {
		return firstCol;
	}

	/**
	 * @param firstCol the firstCol to set
	 */
	public void setFirstCol(int firstCol) {
		this.firstCol = firstCol;
	}

	/**
	 * @return the lastCol
	 */
	public int getLastCol() {
		return lastCol;
	}

	/**
	 * @param lastCol the lastCol to set
	 */
	public void setLastCol(int lastCol) {
		this.lastCol = lastCol;
	}

	@Override
	public String toString() {
		return "CellRange [firstRow=" + firstRow + ", lastRow=" + lastRow + ", firstCol=" + firstCol + ", lastCol="
				+ lastCol + "]";
	}
}
