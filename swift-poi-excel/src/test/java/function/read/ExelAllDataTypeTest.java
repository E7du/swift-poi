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
package function.read;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import cn.zhucongqi.excel.Reader;
import cn.zhucongqi.excel.kit.FileKit;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import junit.framework.TestCase;

/**
 * Created by Jobsz on 17/3/15.
 *
 * @author Jobsz
 * @date 2017/03/15
 */
public class ExelAllDataTypeTest extends TestCase {
    // 创建没有自定义模型,没有sheet的解析器,默认解析所有sheet解析结果以List<String>的方式通知监听者
    @Test
    public void testExcel2007WithReflectModel() {
        InputStream inputStream = FileKit.getInputStream("test2.xlsx");

        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener<?> listener = new ExcelListener();

            new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener).read(new Sheet(1, 1,null));
        }catch (Exception e){
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
