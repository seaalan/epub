package controllers.utility;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/17/2015
 */
public class Word2HtmlUtil {
    private final static String midFolder = "folder";

    /**
     * docx格式word转换为html
     *
     * @param filePath   docx file path
     * @param outFilePath html file out path
     */
    public static Map docx2Html(String filePath, String outFilePath) throws TransformerException, IOException, ParserConfigurationException {

        long startTime = System.currentTimeMillis();

        //read docx file info
        XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));
        //XWPFWordExtractor extractor = new XWPFWordExtractor(document);

        Map metadata = new HashMap<>();
        metadata.put("author",document.getProperties().getCoreProperties().getCreator());
        metadata.put("publisher",document.getProperties().getExtendedProperties().getCompany());

        XHTMLOptions options = XHTMLOptions.create().indent(4);
        //extract pic from docx file and put it to image Folder e.g.:D://eee//folder//
        String imageFolderPath = StringUtil.getFilePath(outFilePath) + midFolder + "//";
        File imageFolder = new File(imageFolderPath);
        options.setExtractor(new FileImageExtractor(imageFolder));
        // URI resolver
        //options.URIResolver(new FileURIResolver(imageFolder));
        options.URIResolver(new BasicURIResolver(midFolder));

        CreateFolderUtil.createFolder(StringUtil.getFilePath(outFilePath));
        OutputStream out = new FileOutputStream(new File(outFilePath + ".html"));

        XHTMLConverter.getInstance().convert(document, out, options);

        System.out.println("Generate " + outFilePath + " with " + (System.currentTimeMillis() - startTime) + " ms.");
        return metadata;
    }

//    /**
//     * docx格式word转换为html
//     *
//     * @param filePath   docx file path
//     * @param outFilePath html file out path
//     */
//    public static Map docx2Htmls(String filePath, String outFilePath) throws TransformerException, IOException, ParserConfigurationException {
//
//        long startTime = System.currentTimeMillis();
//
//        //read docx file info
//        XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));
//        Map metadata = new HashMap<>();
//        metadata.put("author",document.getProperties().getCoreProperties().getCreator());
//        metadata.put("publisher",document.getProperties().getExtendedProperties().getCompany());
//
//
////        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
////
////        while (itPara.hasNext()) {
////            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
////            int length = paragraph.getRuns().size();
////            if (length > 0) {
////                String text = StringUtils.join(paragraph.getRuns().toArray());
////                if (text.indexOf(text) < 0) {
////                    continue;
////                }
////                for (int i = (length - 1); i >= 0; i--) {
////                    paragraph.removeRun(i);
////                }
////                XWPFRun newRun = paragraph.insertNewRun(0);
////                text = text.replaceAll(text, "");
////                newRun.setText(text, 0);
////            }
////        }
//
//
//
//
//
//
//
//
//
//        int j = 1;
//        for(XWPFParagraph doc : document.getParagraphs()){
//
////            int length = doc.getRuns().size();
////            if (length > 0) {
////                String text = StringUtils.join(doc.getRuns().toArray());
////                if (text.indexOf(text) < 0) {
////                    continue;
////                }
////                for (int i = (length - 1); i >= 0; i--) {
////                    doc.removeRun(i);
////                }
////                XWPFRun newRun = doc.insertNewRun(0);
////                text = text.replaceAll(text, "");
////                newRun.setText(text, 0);
////            }
//
//
//
//
//
//
//
//
//
//
//
//            String outFilePathTemp = outFilePath + j + ".html";
//            XWPFDocument docx = doc.getDocument();
//            XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
//            String iiw = doc.getParagraphText();
//            String ii = extractor.getText();
//
//            XHTMLOptions options = XHTMLOptions.create().indent(4);
//            //extract pic from docx file and put it to image Folder e.g.:D://eee//folder//
//            String imageFolderPath = StringUtil.getFilePath(outFilePathTemp) + midFolder + "//";
//            File imageFolder = new File(imageFolderPath);
//            options.setExtractor(new FileImageExtractor(imageFolder));
//            // URI resolver
//            //options.URIResolver(new FileURIResolver(imageFolder));
//            options.URIResolver(new BasicURIResolver(midFolder));
//
//            CreateFolderUtil.createFolder(StringUtil.getFilePath(outFilePathTemp));
//            OutputStream out = new FileOutputStream(new File(outFilePathTemp));
//
//            XHTMLConverter.getInstance().convert(docx, out, options);
//            j++;
//        }
//
//        System.out.println("Generate " + outFilePath + " with " + (System.currentTimeMillis() - startTime) + " ms.");
//        return metadata;
//    }
}
