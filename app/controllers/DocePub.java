package controllers;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/16/2015
 */
public class DocePub {
    public static void docePub(File f)throws IOException {
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


}
