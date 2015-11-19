package controllers;

import controllers.utility.Txt2HtmlUtil;

import java.io.*;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/16/2015
 */
public class TxtePub {
    public static void txtePub(File f) throws IOException {
        AddePub.addePub(f);
    }

    /**
     * convert docx file to html file
     *
     * @param filePath   docx file path e.g.:D://eee.docx
     * @param outFilePath html out path e.g.:D://
     */
    public static void txt2ePub(String filePath, String outFilePath) throws IOException {

        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader read = new InputStreamReader(fileInputStream, "GBK");//考虑到编码格式

        BufferedReader bufferedReader = new BufferedReader(read);

        Txt2HtmlUtil.txt2Html("Make Html", bufferedReader.toString(), "秋水");


        //String nameWithSuffix = StringUtil.getFileName(fileName, true);//e.g.:eee.docx
//        String nameWithoutSuffix = StringUtil.getFileName(filePath, false);//e.g.:eee
//        String pathWithoutSuffix = outFilePath + nameWithoutSuffix + "//" + nameWithoutSuffix;//e.g.:D://eee//eee
//        try {
//            //one: convert docx to html into a folder
//            Word2HtmlUtil.docx2Html(filePath, pathWithoutSuffix + ".html");
//            //two: create epub from html folder
//            CreateePub.createePubFromFolder(outFilePath + nameWithoutSuffix, pathWithoutSuffix + ".epub");
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
    }
}
