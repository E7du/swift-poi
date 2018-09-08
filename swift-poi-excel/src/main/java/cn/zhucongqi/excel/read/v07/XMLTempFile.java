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
package cn.zhucongqi.excel.read.v07;

import java.io.File;
import java.security.SecureRandom;

import cn.zhucongqi.excel.kit.TempFile;

/**
 * @author Jobsz
 *
 */
public class XMLTempFile {

    private static final String TMP_FILE_NAME = "tmp.xlsx";

    private static final String XL = "xl";

    private static final String XML_WORKBOOK = "workbook.xml";

    private static final String XML_SHARED_STRING = "sharedStrings.xml";

    private static final String SHEET = "sheet";

    private static final String WORK_SHEETS = "worksheets";

    private static final SecureRandom random = new SecureRandom();

    public static String getTmpFilePath(String path) {
        return path + File.separator + TMP_FILE_NAME;
    }

    public static String createPath() {
        return TempFile.getSwiftTmpDir() + File.separator + random.nextLong();
    }

    public static String getWorkBookFilePath(String path) {
        return path + File.separator + XL + File.separator + XML_WORKBOOK;
    }

    public static String getSharedStringFilePath(String path) {
        return path + File.separator + XL + File.separator + XML_SHARED_STRING;
    }

    public static String getSheetFilePath(String path, int id) {
        return path + File.separator + XL + File.separator + WORK_SHEETS + File.separator + SHEET + id
            + ".xml";
    }
}
