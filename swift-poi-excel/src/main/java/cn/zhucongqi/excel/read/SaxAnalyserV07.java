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
package cn.zhucongqi.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbookPr;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorkbookDocument;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.zhucongqi.excel.kit.FileKit;
import cn.zhucongqi.excel.metadata.Sheet;
import cn.zhucongqi.excel.read.context.AnalysisContext;
import cn.zhucongqi.excel.read.exception.AnalysisException;
import cn.zhucongqi.excel.read.v07.RowHandler;
import cn.zhucongqi.excel.read.v07.XMLTempFile;
import cn.zhucongqi.excel.read.v07.XmlParserFactory;

/**
 * @author Jobsz
 */
public class SaxAnalyserV07 extends BaseSaxAnalyser {

    private SharedStringsTable sharedStringsTable;

    private List<String> sharedStringList = new LinkedList<String>();

    private List<SheetSource> sheetSourceList = new ArrayList<SheetSource>();

    private boolean use1904WindowDate = false;

    private final String path;

    private File tmpFile;

    private String workBookXMLFilePath;

    private String sharedStringXMLFilePath;

    public SaxAnalyserV07(AnalysisContext analysisContext) throws Exception {
        this.analysisContext = analysisContext;
        this.path = XMLTempFile.createPath();
        this.tmpFile = new File(XMLTempFile.getTmpFilePath(path));
        this.workBookXMLFilePath = XMLTempFile.getWorkBookFilePath(path);
        this.sharedStringXMLFilePath = XMLTempFile.getSharedStringFilePath(path);
        start();
    }

    @Override
    protected void execute() {
        try {
            Sheet sheet = analysisContext.getCurrentSheet();
            if (!isAnalysisAllSheets(sheet)) {
                if (this.sheetSourceList.size() < sheet.getSheetNo() || sheet.getSheetNo() == 0) {
                    return;
                }
                InputStream sheetInputStream = this.sheetSourceList.get(sheet.getSheetNo() - 1).getInputStream();
                parseXmlSource(sheetInputStream);
                return;
            }
            int i = 0;
            for (SheetSource sheetSource : this.sheetSourceList) {
                i++;
                this.analysisContext.setCurrentSheet(new Sheet(i));
                parseXmlSource(sheetSource.getInputStream());
            }

        } catch (Exception e) {
            stop();
            throw new AnalysisException(e);
        } finally {

        }

    }

    private boolean isAnalysisAllSheets(Sheet sheet) {
        if (sheet == null) {
            return true;
        }
        if (sheet.getSheetNo() < 0) {
            return true;
        }
        return false;
    }

    public void stop() {
        for (SheetSource sheet : sheetSourceList) {
            if (sheet.getInputStream() != null) {
                try {
                    sheet.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        FileKit.deletefile(path);
    }

    private void parseXmlSource(InputStream inputStream) {
        try {
            ContentHandler handler = new RowHandler(this, this.sharedStringsTable, this.analysisContext,
                sharedStringList);
            XmlParserFactory.parse(inputStream, handler);
            inputStream.close();
        } catch (Exception e) {
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            throw new AnalysisException(e);
        }
    }

    public List<Sheet> getSheets() {
        List<Sheet> sheets = new ArrayList<Sheet>();
        try {
            int i = 1;
            for (SheetSource sheetSource : this.sheetSourceList) {
                Sheet sheet = new Sheet(i, 0);
                sheet.setSheetName(sheetSource.getSheetName());
                i++;
                sheets.add(sheet);
            }
        } catch (Exception e) {
            stop();
            throw new AnalysisException(e);
        } finally {

        }

        return sheets;
    }

    private void start() throws IOException, XmlException, ParserConfigurationException, SAXException {

        createTmpFile();

        unZipTempFile();

        initSharedStringsTable();

        initUse1904WindowDate();

        initSheetSourceList();

    }

    private void createTmpFile() throws FileNotFoundException {
        FileKit.writeFile(tmpFile, analysisContext.getInputStream());
    }

    private void unZipTempFile() throws IOException {
        FileKit.doUnZip(path, tmpFile);
    }

    private void initSheetSourceList() throws IOException, ParserConfigurationException, SAXException {
        this.sheetSourceList = new ArrayList<SheetSource>();
        InputStream workbookXml = new FileInputStream(this.workBookXMLFilePath);
        XmlParserFactory.parse(workbookXml, new DefaultHandler() {

            private int id = 0;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
                if (qName.toLowerCase(Locale.US).equals("sheet")) {
                    String name = null;
                    id++;
                    for (int i = 0; i < attrs.getLength(); i++) {
                        if (attrs.getLocalName(i).toLowerCase(Locale.US).equals("name")) {
                            name = attrs.getValue(i);
                        } else if (attrs.getLocalName(i).toLowerCase(Locale.US).equals("r:id")) {
                            //id = Integer.parseInt(attrs.getValue(i).replaceAll("rId", ""));
                            try {
                                InputStream inputStream = new FileInputStream(XMLTempFile.getSheetFilePath(path, id));
                                sheetSourceList.add(new SheetSource(id, name, inputStream));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }

        });
        workbookXml.close();
        Collections.sort(sheetSourceList);
    }

    private void initUse1904WindowDate() throws IOException, XmlException {
        InputStream workbookXml = new FileInputStream(workBookXMLFilePath);
        WorkbookDocument ctWorkbook = WorkbookDocument.Factory.parse(workbookXml);
        CTWorkbook wb = ctWorkbook.getWorkbook();
        CTWorkbookPr prefix = wb.getWorkbookPr();
        if (prefix != null) {
            this.use1904WindowDate = prefix.getDate1904();
        }
        this.analysisContext.setUse1904WindowDate(use1904WindowDate);
        workbookXml.close();
    }

    private void initSharedStringsTable() throws IOException, ParserConfigurationException, SAXException {

        InputStream inputStream = new FileInputStream(this.sharedStringXMLFilePath);
        //this.sharedStringsTable = new SharedStringsTable();
        //this.sharedStringsTable.readFrom(inputStream);

        XmlParserFactory.parse(inputStream, new DefaultHandler() {
            //int lastElementPosition = -1;
            //
            //int lastHandledElementPosition = -1;

            String beforeQName = "";

            String currentQName = "";

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                //if (hasSkippedEmptySharedString()) {
                //    sharedStringList.add("");
                //}
                //if ("t".equals(qName)) {
                //    lastElementPosition++;
                //}
                if ("si".equals(qName) || "t".equals(qName)) {
                    beforeQName = currentQName;
                    currentQName = qName;
                }

            }
            //@Override
            //public void endElement (String uri, String localName, String qName)
            //    throws SAXException
            //{
            //    if ("si".equals(qName) || "t".equals(qName)) {
            //        beforeQName = qName;
            //        currentQName = "";
            //    }
            //}

            //private boolean hasSkippedEmptySharedString() {
            //    return lastElementPosition > lastHandledElementPosition;
            //}

            @Override
            public void characters(char[] ch, int start, int length) {
                if ("t".equals(currentQName) && ("t".equals(beforeQName))) {
                    String pre = sharedStringList.get(sharedStringList.size() - 1);
                    String str = pre + new String(ch, start, length);
                    sharedStringList.remove(sharedStringList.size() - 1);
                    sharedStringList.add(str);
                }else  if ("t".equals(currentQName) && ("si".equals(beforeQName))){
                    sharedStringList.add(new String(ch, start, length));
                }
               // lastHandledElementPosition++;


            }

        });
        inputStream.close();
    }

    private class SheetSource implements Comparable<SheetSource> {

        private int id;

        private String sheetName;

        private InputStream inputStream;

        public SheetSource(int id, String sheetName, InputStream inputStream) {
            this.id = id;
            this.sheetName = sheetName;
            this.inputStream = inputStream;
        }

        public String getSheetName() {
            return sheetName;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public int compareTo(SheetSource o) {
            if (o.id == this.id) {
                return 0;
            } else if (o.id > this.id) {
                return -1;
            } else {
                return 1;
            }
        }
    }

}
