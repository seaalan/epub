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
//        String Title = summaryInformation.getTitle();
//        String Author = summaryInformation.getAuthor();
        fis.close();
        AddePub.addePub(summaryInformation, text);
    }

    public static void docx2ePub(String fileName, String outPutFile) throws IOException {
        System.out.println(fileName);
        System.out.println(StringUtil.getFileName(fileName, true));
        System.out.println(StringUtil.getFileName(fileName, false));
        //D://ee.docx
        //D://ee//ee1.html
        String outPutFile1 = outPutFile + StringUtil.getFileName(fileName, false) + "//" + StringUtil.getFileName(fileName, false) + ".html";
        try {
            //convert docx to html
            Word2HtmlUtil.docx2Html(fileName, outPutFile1);
            //D://ee
            //D://ee//ee.epub
            System.out.println(outPutFile + StringUtil.getFileName(fileName, false));
            System.out.println(outPutFile + StringUtil.getFileName(fileName, false) + "//" + StringUtil.getFileName(fileName, false) + ".epub");
            //
            CreateePub.createePubFromFolder(outPutFile + StringUtil.getFileName(fileName, false),
                    outPutFile + StringUtil.getFileName(fileName, false) + "//" + StringUtil.getFileName(fileName, false) + ".epub");
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
