package controllers;

import controllers.utility.StringUtil;
import controllers.utility.Word2HtmlUtil;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/16/2015
 */
public class DocePub {
    public static void docePub(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        HWPFDocument doc = new HWPFDocument(fis);
        Range rang = doc.getRange();
        String text = rang.text().toString();
        SummaryInformation summaryInformation = doc.getSummaryInformation();
        fis.close();
        AddePub.addePub(summaryInformation, text);
    }

    /**
     * convert docx file to ePub file
     *
     * @param filePath   docx file path e.g.:D://eee.docx
     * @param outFilePath html out path e.g.:D://
     */
    public static void docx2ePub(String filePath, String outFilePath) throws IOException {
        //String nameWithSuffix = StringUtil.getFileName(fileName, true);//e.g.:eee.docx
        String nameWithoutSuffix = StringUtil.getFileName(filePath, false);//e.g.:eee
        String pathWithoutSuffix = outFilePath + nameWithoutSuffix + "//" + nameWithoutSuffix;//e.g.:D://eee//eee
        try {
            //one: convert docx to html into a folder
            Map metadata = Word2HtmlUtil.docx2Html(filePath, pathWithoutSuffix + ".html");
            //two: create epub from html folder
            CreateePub.createePubFromFolder(outFilePath + nameWithoutSuffix, pathWithoutSuffix + ".epub", metadata);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
