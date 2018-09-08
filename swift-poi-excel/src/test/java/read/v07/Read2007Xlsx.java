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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import cn.zhucongqi.excel.Reader;
import cn.zhucongqi.excel.kit.FileKit;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.context.AnalysisContext;
import cn.zhucongqi.excel.read.event.AnalysisEventListener;
import cn.zhucongqi.excel.support.ExcelTypeEnum;
import javamodel.ExcelRowJavaModel;
import javamodel.ExcelRowJavaModel1;

/**
 * @author Jobsz
 * @date 2017/08/27
 */
public class Read2007Xlsx {
    //创建没有自定义模型,没有sheet的解析器,默认解析所有sheet解析结果以List<String>的方式通知监听者
    @Test
    public void noModel() {
        InputStream inputStream = FileKit.getInputStream("1.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<List<String>>() {
                    @Override
                    public void invoke(List<String> object, AnalysisContext context) {
                        System.out.println(
                            "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                + " data:" + object);

                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });

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
    public void withJavaModel() {
        InputStream inputStream = FileKit.getInputStream("2007WithModel.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<ExcelRowJavaModel>() {
                    @Override
                    public void invoke(ExcelRowJavaModel object, AnalysisContext context) {
                        System.out.println(
                            "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                + " data:" + object);

                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });

            reader.read(new Sheet(1, 2, ExcelRowJavaModel.class));
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

    //创建没有自定义模型,没有sheet的解析器,默认解析所有sheet解析结果以List<String>的方式通知监听者
    @Test
    public void noModelMultipleSheet() {
        InputStream inputStream = FileKit.getInputStream("2007NoModelMultipleSheet.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<List<String>>() {
                    @Override
                    public void invoke(List<String> object, AnalysisContext context) {
                        System.out.println(
                            "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                + " data:" + object);

                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });

            reader.read();
            //reader.finish();
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
    public void withModelMultipleSheet() {
        InputStream inputStream = FileKit.getInputStream("2007WithModelMultipleSheet.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<Object>() {
                    @Override
                    public void invoke(Object object, AnalysisContext context) {
                        ExcelRowJavaModel obj = null;
                        if (context.getCurrentSheet().getSheetNo() == 1) {
                            obj = (ExcelRowJavaModel)object;
                        }
                        if (context.getCurrentSheet().getSheetNo() == 2) {
                            obj = (ExcelRowJavaModel)object;
                        }
                        System.out.println(
                            "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                + " data:" + obj);

                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {

                    }
                });

            reader.read(new Sheet(1, 2, ExcelRowJavaModel.class));
            reader.read(new Sheet(2, 1, ExcelRowJavaModel1.class));

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
    public void withModelMultipleSheet1() {
        InputStream inputStream = FileKit.getInputStream("sss.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<Object>() {
                    @Override
                    public void invoke(Object object, AnalysisContext context) {
                        ExcelRowJavaModel1 obj = null;
                        if (context.getCurrentSheet().getSheetNo() == 1) {
                            obj = (ExcelRowJavaModel1)object;
                        }
                        //if (context.getCurrentSheet().getSheetNo() == 2) {
                        //    obj = (ExcelRowJavaModel)object;
                        //}
                        System.out.println(
                            "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                + " data:" + obj);

                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {

                    }
                });

            reader.read(new Sheet(1, 1, ExcelRowJavaModel1.class));
           // reader.read(new Sheet(2, 1, ExcelRowJavaModel1.class));

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

    //读取shets
    @Test
    public void getSheets() {
        InputStream inputStream = FileKit.getInputStream("2007NoModelMultipleSheet.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<List<String>>() {
                    @Override
                    public void invoke(List<String> object, AnalysisContext context) {
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });

            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
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

    //获取sheet后再单独一个sheet解析
    @Test
    public void getSheetsAndAnalysisNoModel() {
        InputStream inputStream = FileKit.getInputStream("2007NoModelMultipleSheet.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<List<String>>() {
                    @Override
                    public void invoke(List<String> object, AnalysisContext context) {
                        System.out.println(
                            "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                + " data:" + object);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });

            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet : sheets) {
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

    /**
     * 获取所有sheet后遍历解析，解析结果含有java模型
     */
    @Test
    public void getSheetsAndAnalysisWithModel() {
        InputStream inputStream = FileKit.getInputStream("2007WithModelMultipleSheet.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<Object>() {
                    @Override
                    public void invoke(Object object, AnalysisContext context) {
                        ExcelRowJavaModel obj = null;
                        if (context.getCurrentSheet().getSheetNo() == 1) {
                            obj = (ExcelRowJavaModel)object;
                        }
                        if (context.getCurrentSheet().getSheetNo() == 2) {
                            obj = (ExcelRowJavaModel)object;
                        }
                        System.out.println(
                            "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                + " data:" + obj);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });

            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet : sheets) {
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

    /**
     * 解析过程中断，不再解析（比如解析到某一行出错了，后面不需要再解析了）
     */
    @Test
    public void interrupt() {
        InputStream inputStream = FileKit.getInputStream("2007WithModelMultipleSheet.xlsx");
        try {
            Reader reader = new Reader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<Object>() {
                    @Override
                    public void invoke(Object object, AnalysisContext context) {
                        context.interrupt();
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                    }
                });

            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet : sheets) {
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

    public static void main(String[] args) {
        List<String> mm = new ArrayList<String>();
        mm.add(null);
        mm.add(null);
        mm.add(null);
        mm.add(null);
        mm.removeAll(Collections.singleton(null));

        System.out.println(mm);
    }
}
