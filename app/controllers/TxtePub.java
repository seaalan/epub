package controllers;

import controllers.utility.StringUtil;
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
     * convert docx file to ePub file
     *
     * @param filePath   docx file path e.g.:D://eee.docx
     * @param outFilePath html out path e.g.:D://
     */
    public static void txt2ePub(String filePath, String outFilePath) throws IOException {

        String nameWithoutSuffix = StringUtil.getFileName(filePath, false);//e.g.:xxx
        String pathWithoutSuffix = outFilePath + nameWithoutSuffix + "//" + nameWithoutSuffix;//e.g.:D://xxx//xxx

        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader read = new InputStreamReader(fileInputStream, "GBK");//考虑到编码格式

        BufferedReader bufferedReader = new BufferedReader(read);
        String txtContent = "";
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            if (lineTxt.length() == 0) continue;//按txt中的每行来循环epub中的每章，txt中的空行忽略
            txtContent = txtContent + lineTxt + "\n";
        }
        //one: convert txt to html into a folder
        Txt2HtmlUtil.txt2Html(txtContent, pathWithoutSuffix + ".html");
        //two: create epub from html folder
        CreateePub.createePubFromFolder(outFilePath + nameWithoutSuffix, pathWithoutSuffix + ".epub");
    }
}
