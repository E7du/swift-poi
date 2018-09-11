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
package cn.zhucongqi.excel.read.event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;

import cn.zhucongqi.excel.kit.TypeKit;
import cn.zhucongqi.excel.metadata.Column;
import cn.zhucongqi.excel.metadata.Rule;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.context.AnalysisContext;
import cn.zhucongqi.excel.write.exception.GenerateException;

public abstract class JFModelReadListener extends AnalysisEventListener<List<String>> {

	private List<Model<?>> datas;
	private Rule rule;

	public JFModelReadListener() {
		this.datas = new ArrayList<Model<?>>();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getDatas() {
		return (T)this.datas;
	}
	
	/**
	 * Excel Read Rule
	 */
	public abstract Rule rule();

	/**
	 * Read a model
	 * @param model
	 */
	public abstract void readRow(Model<?> model);
	
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void invoke(List<String> object, AnalysisContext context) {
		if (null == this.getRule()) {
			throw (new GenerateException("Please set read rule first."));
		}
		Sheet sheet = context.getCurrentSheet();
		Integer currentRow = context.getCurrentRowIdx();
		if (sheet.hasHeader() && currentRow <= sheet.getHeaderLineCnt()) {
			return;
		}
		
		Class<?> clazz = (Class<?>) context.getCustom();
		Model<?>  model = null;
		if (Model.class.isAssignableFrom(clazz)) {
			try {
				model = (Model<?>) clazz.newInstance();
			} catch (Exception e) {
				throw new GenerateException(e);
			}
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(byteOut);
				out.writeObject(object);
				ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
				ObjectInputStream in = new ObjectInputStream(byteIn);
				List<String> dest = (List<String>) in.readObject();
				
				// put data to model
				Column col;
				for (int i = 0; i < dest.size(); i++) {
					col = this.getRule().getColumn(i);
					if (null == col) {
						continue;
					}
					String val = dest.get(i);
					if (null != val) {
						String attr = col.getAttr();
						Object value = TypeKit.convert(val, col.getType(), col.getDateFormat(), context.use1904WindowDate());
						if (value != null) {
							model.set(attr, value);
						}
					}
				}
			} catch (Exception e) {
				throw new GenerateException(e);
			} finally {
				if (null != out) {
					try {
						out.close();
					} catch (IOException e) {
						throw new GenerateException(e);
					}
				}
				if (null != byteOut) {
					try {
						byteOut.close();
					} catch (IOException e) {
						throw new GenerateException(e);
					}
				}
			}
		}
		
		this.datas.add(model);
		this.readRow(model);
	}
	
	private Rule getRule() {
		if (null == this.rule) {
			this.rule = this.rule();
		}
		return this.rule;
	}
}
