package controllers.convert;

import controllers.crud.CreateePub;
import controllers.utility.Email2HtmlUtil;
import controllers.utility.Txt2HtmlUtil;

import java.util.HashMap;
import java.util.Map;

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
    public static void email2ePub(String emailSubject, String outFilePath) throws Exception {
        String nameWithoutSuffix = emailSubject;//e.g.:sea
        String pathWithoutSuffix = outFilePath + nameWithoutSuffix + "//" + nameWithoutSuffix;//e.g.:D://sea//sea

        //one: convert email to html into a folder
        //String emailContent = Email2HtmlUtil.receive(emailSubject);
        String emailContent = Email2HtmlUtil.receiveEmails(emailSubject, "");
        Txt2HtmlUtil.txt2Html(emailContent, pathWithoutSuffix + ".html");

        //two: create epub from html folder
        Map metadata = new HashMap<>();
        metadata.put("author","");
        metadata.put("publisher","");
        CreateePub.createePubFromFolder(outFilePath + nameWithoutSuffix, pathWithoutSuffix + ".epub", metadata);
    }

    /**
     * convert email file to ePub file
     *
     * @param emailSubject   docx file path e.g.:D://eee
     * @param outFilePath html out path e.g.:D://
     */
    public static String emailAttachment2ePub(String emailSubject, String outFilePath) throws Exception {
        //save email attachment into a folder
        String filePath = Email2HtmlUtil.receiveEmails(emailSubject, outFilePath);
        return filePath;
    }
}
