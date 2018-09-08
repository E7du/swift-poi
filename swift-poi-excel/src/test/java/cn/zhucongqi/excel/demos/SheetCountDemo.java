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
package cn.zhucongqi.excel.demos;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import cn.zhucongqi.excel.consts.SheetConsts;

class SheetCountDemo {

	@Test
	void test() {
		
		Integer dataSize = SheetConsts.SHEET_MAX_ROW * 4 - 1;
		
		Integer cnt = this.sheetCount(dataSize);
		System.out.println("datasize"+dataSize+"cnt"+cnt);
		
		List<String> datas = new ArrayList<String>();
		for (int i = 0; i < dataSize ; i++) {
			datas.add("data"+i);
		}
		
		for (int i = 0; i < cnt; i++) {
			int end = (i+1) * SheetConsts.SHEET_MAX_ROW;
			end = end > dataSize ? dataSize : end;
			
			int start = i * SheetConsts.SHEET_MAX_ROW;
			List<String> tmp = datas.subList(start , end);
			System.out.println("s="+tmp.get(0)+" e="+tmp.get(tmp.size()-1) + "end ="+end);
			System.out.println(tmp.size());
		}
	}

	private Integer sheetCount(Integer dataSize) {
		if (dataSize == 0) {
			return 1;
		}
		return (int) (Math.ceil(dataSize / (SheetConsts.SHEET_MAX_ROW * 1.0)));
	}
}
