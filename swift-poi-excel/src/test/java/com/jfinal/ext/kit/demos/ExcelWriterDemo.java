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
package com.jfinal.ext.kit.demos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.test.api.model.User;

import cn.zhucongqi.excel.Writer;
import cn.zhucongqi.excel.metadata.Header;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.support.ExcelTypeEnum;

class ExcelWriterDemo {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 665; i++) {
			User u = new User();
			u.setId(i+3);
			u.setName("名字"+i);
			u.setAddr("addr"+i);
			users.add(u);
		}
		
		try {
			OutputStream out = new FileOutputStream("./src/test/resources/userswrite_sh.xls");
			Writer writer = new Writer(out, ExcelTypeEnum.XLS);
			
			Sheet sh = new Sheet(1, 0);
			Header header = new Header(1, new String[] {"姓名", "ID", "地址"});
			
			sh.setHeader(header);
			writer.write(users, sh);
			writer.finish();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
