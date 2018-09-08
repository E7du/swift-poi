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
package read.v07;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import cn.zhucongqi.excel.Reader;
import cn.zhucongqi.excel.Writer;
import cn.zhucongqi.excel.kit.FileKit;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import javamodel.ExcelRowJavaModel;
import javamodel.ExcelRowJavaModel1;
import read.v07.listener.Excel2007NoJavaModelAnalysisListener;
import read.v07.listener.Excel2007WithJavaModelAnalysisListener;

/**
 * @author Jobsz
 * @date 2017/08/27
 */
public class Read2007MeanWhileWrite {

    @Test
    public void noModel() {

        InputStream inputStream = FileKit.getInputStream("2007NoModelMultipleSheet.xlsx");
        try {
            Excel2007NoJavaModelAnalysisListener listener = new Excel2007NoJavaModelAnalysisListener();
            Writer writer = new Writer(new FileOutputStream("./src/test/resources/77.xlsx"),
                ExcelTypeEnum.XLSX, false);
            listener.setExcelWriter(writer);
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet : sheets) {
                sheet.setHeadLineMun(1);
                reader.read(sheet);
            }
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
    public void withModel() {

        InputStream inputStream = FileKit.getInputStream("2007WithModelMultipleSheet.xlsx");
        try {
            Excel2007WithJavaModelAnalysisListener listener = new Excel2007WithJavaModelAnalysisListener();
            Writer writer = new Writer(new FileOutputStream("./src/test/resources/78_withmodel.xlsx"),
                ExcelTypeEnum.XLSX, true);
            listener.setExcelWriter(writer);
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            List<Sheet> sheets = reader.getSheets();
            for (Sheet sheet : sheets) {
                sheet.setHeadLineMun(1);
                if (sheet.getSheetNo() == 1) {
                    sheet.setHeadLineMun(2);
                    sheet.setClazz(ExcelRowJavaModel.class);
                }
                if (sheet.getSheetNo() == 2) {
                    sheet.setHeadLineMun(1);
                    sheet.setClazz(ExcelRowJavaModel1.class);
                }
                reader.read(sheet);
            }
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
}
