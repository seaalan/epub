package controllers;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.List;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p/>
 * Created by alex on 11/17/2015
 */
public class Word2Html {
    private final static String  tempPath = "D://eee//folder//";

    public static void main(String argv[]) {
        try {
            docx2Html("D://ee.docx", "D://ee//ee.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * docx格式word转换为html
     *
     * @param fileName
     *            docx文件路径
     * @param outPutFile
     *            html输出文件路径
     * @throws javax.xml.transform.TransformerException
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static void docx2Html(String fileName, String outPutFile) throws TransformerException, IOException, ParserConfigurationException {
        //html文件输出路径
        String fileOutName = outPutFile;
        long startTime = System.currentTimeMillis();
        //读取docx文件信息
        XWPFDocument document = new XWPFDocument(new FileInputStream(fileName));
        XHTMLOptions options = XHTMLOptions.create().indent(4);
        //提取docx文件中的图片
        File imageFolder = new File(tempPath);
        options.setExtractor(new FileImageExtractor(imageFolder));
        // URI resolver
        //options.URIResolver(new FileURIResolver(imageFolder));
        options.URIResolver(new BasicURIResolver("folder"));
        File outFile = new File(fileOutName);
        outFile.getParentFile().mkdirs();
        OutputStream out = new FileOutputStream(outFile);
        XHTMLConverter.getInstance().convert(document, out, options);
        System.out.println("Generate " + fileOutName + " with " + (System.currentTimeMillis() - startTime) + " ms.");

    }


    public static void docx2Html2(String fileName) throws TransformerException, IOException, ParserConfigurationException {

        XWPFDocument document = new XWPFDocument( new FileInputStream(fileName) );
        XHTMLOptions options = XHTMLOptions.create().indent( 4 );
        // Extract image
        List<XWPFPictureData> pics=document.getAllPictures();
        for (XWPFPictureData pic : pics) {
            System.out.println(pic.getPictureType() + pic.suggestFileExtension()
                    +pic.getFileName());
            byte[] bytev = pic.getData();
            FileOutputStream fos = new FileOutputStream("D:\\eee\\"+pic.getFileName());
            fos.write(bytev);
            fos.close();
        }
        File imageFolder = new File( "D:\\eee\\images" );
        options.setExtractor( new FileImageExtractor( imageFolder ) );
        // URI resolver
        options.URIResolver(new BasicURIResolver("images"));

        File outFile = new File( "D:\\eee\\" );
        outFile.getParentFile().mkdirs();
        OutputStream out = new FileOutputStream( outFile );
        XHTMLConverter.getInstance().convert( document, out, options );
    }
}
