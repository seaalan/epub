package controllers.convert;

import controllers.crud.AddePub;
import controllers.crud.CreateePub;
import controllers.utility.FileUtil;
import controllers.utility.Html2Xhtml;
import controllers.utility.HtmlUtil;
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
     * @param filePath    docx file path e.g.:D://eee.docx
     * @param outFilePath html out path e.g.:D://
     */
    public static void docx2ePub(String filePath, String outFilePath) throws IOException {
        //String nameWithSuffix = StringUtil.getFileName(fileName, true);//e.g.:eee.docx
        String nameWithoutSuffix = FileUtil.getFileName(filePath, false);//e.g.:eee
        String pathWithoutSuffix = outFilePath + nameWithoutSuffix + "//" + nameWithoutSuffix;//e.g.:D://eee//eee
        try {
            //one: convert docx to html into a folder
            Map metadata = Word2HtmlUtil.docx2Html(filePath, pathWithoutSuffix);

            Html2Xhtml.docxhtml2Xhtml(pathWithoutSuffix + ".xhtml", pathWithoutSuffix + ".xhtml");
            //two: create epub from html folder
            CreateePub.createePubFromFolder(outFilePath + nameWithoutSuffix, pathWithoutSuffix + ".epub", metadata);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * convert docx file to ePub file
     *
     * @param filePath docx file path e.g.:D://eee.docx
     */
    public static void docx2ePubWithManyChapter(String filePath, String outFilePath) throws Exception {
        String nameWithoutSuffix = FileUtil.getFileName(filePath, false);
        String pathWithoutSuffix = outFilePath + nameWithoutSuffix + "//" + nameWithoutSuffix;
        //one: convert docx to html into a folder
        Map metadata = Word2HtmlUtil.docx2Html(filePath, pathWithoutSuffix);
        HtmlUtil.html2ParagraphHtml(pathWithoutSuffix.replace("//", "\\"));
        //two: create epub from html folder
        CreateePub.createePubFromFolder(outFilePath + nameWithoutSuffix, pathWithoutSuffix + ".epub", metadata);
    }
}
