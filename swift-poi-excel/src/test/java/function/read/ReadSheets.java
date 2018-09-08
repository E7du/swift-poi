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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import cn.zhucongqi.excel.Reader;
import cn.zhucongqi.excel.Writer;
import cn.zhucongqi.excel.kit.FileKit;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;

/**
 * Created by Jobsz on 17/3/22.
 *
 * @author Jobsz
 * @date 2017/03/22
 */
public class ReadSheets {
	
    @Test
    public void ReadSheets2007() {
        InputStream inputStream = FileKit.getInputStream("2007NoModelMultipleSheet.xlsx");
        try {
            ExcelListener listener = new ExcelListener();
            listener.setSheet(new Sheet(1));
           
            Writer writer = new Writer(new FileOutputStream("./src/test/resources/2007_write.xlsx"), ExcelTypeEnum.XLSX, true);
            listener.setWriter(writer);
           
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet:sheets) {
                sheet.setHeadLineMun(1);
                reader.read(sheet);
            }
         // reader.read(new Sheet(1));
            writer.finish();

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
    public void ReadSheets2003() {
        InputStream inputStream = FileKit.getInputStream("2003.xls");
        try {
            AnalysisEventListener<?> listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLS, null, listener);
            reader.read();
            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet:sheets) {
                sheet.setHeadLineMun(1);
                reader.read(sheet);
            }
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
