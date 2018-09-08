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
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import function.model.OneRowHeadExcelModel;
import junit.framework.TestCase;

/**
 * Created by Jobsz on 17/2/18.
 */
public class XLSX2007FunctionTest extends TestCase {

    //创建没有自定义模型,没有sheet的解析器,默认解析所有sheet解析结果以List<String>的方式通知监听者
    @Test
    public void testExcel2007NoModel() {
        InputStream inputStream = getInputStream("2007NoModelBigFile.xlsx");
        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener<?> listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);

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
    public void testExcel2007NoModel2() {
        InputStream inputStream = getInputStream("test4.xlsx");
        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener<?> listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);

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

    //创建没有自定义模型,但有规定sheet解析器,解析结果以List<String>的方式通知监听者
    @Test
    public void testExcel2007WithSheet() {
        InputStream inputStream = getInputStream("111.xlsx");

        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener<?> listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read(new Sheet(1, 0));
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

    //创建需要反射映射模型的解析器,解析结果List<Object> Object为自定义的模型
    @Test
    public void testExcel2007WithReflectModel() {
        InputStream inputStream = getInputStream("2007.xlsx");
        try {

            // 解析每行结果在listener中处理
            AnalysisEventListener<?> listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);

            reader.read(new Sheet(1, 1, OneRowHeadExcelModel.class));
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
    public void testExcel2007MultHeadWithReflectModel() {
        InputStream inputStream = getInputStream("2007_1.xlsx");

        try {

            // 解析每行结果在listener中处理
            AnalysisEventListener<?> listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);

            reader.read(new Sheet(1, 4, OneRowHeadExcelModel.class));

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

    private InputStream getInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);

    }
}
