package controllers.utility;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
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
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);

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
        OutputStream out = new FileOutputStream(new File(outFilePath));

        XHTMLConverter.getInstance().convert(document, out, options);

        System.out.println("Generate " + outFilePath + " with " + (System.currentTimeMillis() - startTime) + " ms.");
        return metadata;
    }
}
