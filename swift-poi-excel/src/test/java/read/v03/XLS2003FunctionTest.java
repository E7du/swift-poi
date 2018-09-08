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
package read.v03;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import cn.zhucongqi.excel.Reader;
import cn.zhucongqi.excel.kit.FileKit;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import function.model.LoanInfo;
import junit.framework.TestCase;

/**
 * Created by Jobsz on 17/2/19.
 */
public class XLS2003FunctionTest extends TestCase {

    @Test
    public void testExcel2003NoModel() {
        InputStream inputStream = FileKit.getInputStream("loan1.xls");
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLS, null, listener);
            reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testExcel2003WithSheet() {
        InputStream inputStream = FileKit.getInputStream("loan1.xls");
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLS, null, listener);
            reader.read(new Sheet(1, 1));

            System.out.println(listener.getDatas());
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testExcel2003WithReflectModel() {
        InputStream inputStream = FileKit.getInputStream("loan1.xls");
        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener<?> listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLS, null, listener);

            reader.read(new Sheet(1, 2, LoanInfo.class));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
