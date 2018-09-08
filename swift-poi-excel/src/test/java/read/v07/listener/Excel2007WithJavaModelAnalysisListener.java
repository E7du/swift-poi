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
package read.v07.listener;

import java.util.ArrayList;
import java.util.List;

import cn.zhucongqi.excel.Writer;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.context.AnalysisContext;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import javamodel.ExcelRowJavaModel;

/**
 * @author Jobsz
 * @date 2017/08/27
 */
public class Excel2007WithJavaModelAnalysisListener extends AnalysisEventListener<Object> {

    private Writer writer;

    public Writer getExcelWriter() {
        return writer;
    }

    public void setExcelWriter(Writer writer) {
        this.writer = writer;
    }

    public void invoke(Object object, AnalysisContext context) {
        List<Object> ll = new ArrayList<Object>();
        ll.add(object);
        Sheet sheet = context.getCurrentSheet();
        sheet.setClazz(ExcelRowJavaModel.class);
        writer.write(ll,sheet);
    }

    public void doAfterAllAnalysed(AnalysisContext context) {

    }

}
