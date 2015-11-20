package controllers;

import controllers.utility.Email2HtmlUtil;
import controllers.utility.Txt2HtmlUtil;

import java.io.IOException;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/16/2015
 */
public class EmailePub {
    /**
     * convert email file to ePub file
     *
     * @param emailSubject   docx file path e.g.:D://eee
     * @param outFilePath html out path e.g.:D://
     */
    public static void email2ePub(String emailSubject, String outFilePath) throws IOException {

        String nameWithoutSuffix = emailSubject;//e.g.:sea
        String pathWithoutSuffix = outFilePath + nameWithoutSuffix + "//" + nameWithoutSuffix;//e.g.:D://sea//sea

        //one: convert email to html into a folder
        try {
            String emailContent = Email2HtmlUtil.receive(emailSubject);
            Txt2HtmlUtil.txt2Html(emailContent, pathWithoutSuffix + ".html");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //two: create epub from html folder
        CreateePub.createePubFromFolder(outFilePath + nameWithoutSuffix, pathWithoutSuffix + ".epub");
    }
}
