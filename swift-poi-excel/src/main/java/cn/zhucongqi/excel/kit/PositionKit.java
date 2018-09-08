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
package cn.zhucongqi.excel.kit;

/**
 * @author Jobsz
 */
public class PositionKit {

    public static int getRow(String currentCellIndex) {
        int row = 0;
        if (currentCellIndex != null) {
            String rowStr = currentCellIndex.replaceAll("[A-Z]", "").replaceAll("[a-z]", "");
            row = Integer.parseInt(rowStr)-1;
        }
        return row;
    }

    public static int getCol(String currentCellIndex) {
        int col = 0;
        if (currentCellIndex != null) {

            char[] currentIndex = currentCellIndex.replaceAll("[0-9]", "").toCharArray();
            for (int i = 0; i < currentIndex.length; i++) {
                col += (currentIndex[i] - '@') * Math.pow(26, (currentIndex.length - i - 1));
            }
        }
        return col-1;
    }
}
