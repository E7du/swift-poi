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
import function.model.TestModel3;

/**
 * Created by Jobsz on 17/3/19.
 *
 * @author Jobsz
 * @date 2017/03/19
 */
public class NumTest3 {

    @Test
    public void testExcel2007WithReflectModel() {
        InputStream inputStream = FileKit.getInputStream("test3.xlsx");
        try {
            AnalysisEventListener<?> listener = new ExcelListener();

            new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener).read(new Sheet(1, 1, TestModel3.class));
        } catch (Exception e) {

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
