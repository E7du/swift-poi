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
package cn.zhucongqi.excel.kit;

import java.io.File;

/**
 * Fix POI {@link org.apache.poi.util.DefaultTempFileCreationStrategy} <br/>
 * In concurrent writes, create a temporary directory BUG that throw an exception.
 *
 * @author Jobsz
 */
public class TempFile {

    private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

    private static final String POIFILES = "poifiles";

    private static final String SWIFT_EXCEL_FILES = "swift_excel";

    /**
     * 在创建ExcelBuilder后尝试创建临时目录，避免poi创建时候报错
     */
    public static void createPOIFilesDirectory() {

        String tmpDir = System.getProperty(JAVA_IO_TMPDIR);
        if (tmpDir == null) {
            throw new RuntimeException(
                "Systems temporary directory not defined - set the -D" + JAVA_IO_TMPDIR + " jvm property!");
        }
        File directory = new File(tmpDir, POIFILES);
        if (!directory.exists()) {
            syncCreatePOIFilesDirectory(directory);
        }

    }

    /**
     * 获取环境变量的配置
     * @return swift-poi-excel临时目录
     */
    public static String getSwiftTmpDir() {
        String tmpDir = System.getProperty(JAVA_IO_TMPDIR);
        if (tmpDir == null) {
            throw new RuntimeException(
                "Systems temporary directory not defined - set the -D" + JAVA_IO_TMPDIR + " jvm property!");
        }
        File directory = new File(tmpDir, SWIFT_EXCEL_FILES);
        if (!directory.exists()) {
            syncCreatePOIFilesDirectory(directory);
        }
        return tmpDir + File.separator + SWIFT_EXCEL_FILES;
    }

    /**
     * 如果directory 不存在则创建
     *
     * @param directory
     */
    private static synchronized void syncCreatePOIFilesDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
