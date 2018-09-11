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
package function.write;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import cn.zhucongqi.excel.Reader;
import cn.zhucongqi.excel.Writer;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;

/**
 * @author Jobsz
 * @date 2017/08/15
 */
public class ExcelWriteTest1 {

    @Test
    public void test(){
        OutputStream out = null;
        try {
            out = new FileOutputStream("/Users/Jobsz/79.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Writer writer = new Writer(out, ExcelTypeEnum.XLSX);

            //写sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("第一个sheet");
            List<String> list = new ArrayList<String>();
            list.add("1");list.add("2");list.add("3");
            List<String> list1 = new ArrayList<String>();
            list1.add("1");list1.add("2");list1.add("3");
            List<List<String>> lll = new ArrayList<List<String>>();
            lll.add(list);
            writer.write0(lll,sheet1);
            writer.write0(lll,sheet1);
            writer.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteAndRead(){
        OutputStream out = null;
        try {
            out = new FileOutputStream("/Users/Jobsz/79.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SXSSFWorkbook wb;
        try {
            wb = new SXSSFWorkbook(10000);
            SXSSFSheet sheet = wb.createSheet("11111");
            Row row = sheet.createRow(0);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("1111");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("22222");
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("33333");



            Row row1 = sheet.createRow(1);
            Cell cell21 = row1.createCell(0);
            cell21.setCellValue("444");
            Cell cell22 = row1.createCell(1);
            cell22.setCellValue("555");
            Cell cell23 = row1.createCell(2);
            cell23.setCellValue("666");
            wb.write(out);
            out.close();




            InputStream inputStream = new FileInputStream("/Users/Jobsz/79.xlsx");

            AnalysisEventListener<?> listener = new ExcelListener();

            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read(new Sheet(1));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
