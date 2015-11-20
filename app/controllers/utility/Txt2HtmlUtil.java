package controllers.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/18/2015
 */
public class Txt2HtmlUtil {
    private final static String htmlTemplatePath = "D://leon.html";

    public static void txt2Html(String content, String outFilePath) {
        try {
            String templateContent = "";
            FileInputStream fileinputstream = new FileInputStream(htmlTemplatePath);// 读取模板文件
            int lenght = fileinputstream.available();
            byte bytes[] = new byte[lenght];
            fileinputstream.read(bytes);
            fileinputstream.close();

            templateContent = new String(bytes);
            System.out.print(templateContent);
            // 替换掉模板中相应的地方
            templateContent = templateContent.replaceAll("###title###", StringUtil.getFileName(outFilePath, false));
            templateContent = templateContent.replaceAll("###content###", content);
            templateContent = templateContent.replaceAll("###author###", StringUtil.getFileName(outFilePath, false));
            System.out.print(templateContent);

            CreateFolderUtil.createFolder(StringUtil.getFilePath(outFilePath));
//            // 根据时间得文件名
//            Calendar calendar = Calendar.getInstance();
//            String fileame = String.valueOf(calendar.getTimeInMillis()) + ".html";
//            fileame = "/" + fileame;// 生成的html文件保存路径。
            FileOutputStream fileoutputstream = new FileOutputStream(outFilePath);// 建立文件输出流
            byte tag_bytes[] = templateContent.getBytes("GBK");//读取文件时指定字符编码
            fileoutputstream.write(tag_bytes);
            fileoutputstream.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
