package controllers.utility;

import java.io.FileNotFoundException;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/27/2015
 */
public class Html2Xhtml {
    public static void html2Xhtml(String filePath, String outFilePath) throws FileNotFoundException {

        try {
            String content = FileUtil.read(filePath, "UTF-8");
            String head = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                    "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
            // 替换掉模板中相应的地方
            content = content.replaceAll("<html>", head);

            FileUtil.createFolder(FileUtil.getFilePath(outFilePath));
            FileUtil.write(content, outFilePath, "UTF-8");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public static void docxhtml2Xhtml(String filePath, String outFilePath) throws FileNotFoundException {

        try {
            String content = FileUtil.read(filePath, "UTF-8");
            String head = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                    "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">";

            String style = "<style type=\"text/css\">";
            String title = "</style><title>xxx</title>";
            String img = "<img alt=\"xxx\" ";
            // 替换掉模板中相应的地方
            content = content.replaceAll("<html>", head);
            content = content.replaceAll("<style>", style);
            content = content.replaceAll("</style>", title);
            content = content.replaceAll("<img ", img);

            FileUtil.createFolder(FileUtil.getFilePath(outFilePath));
            FileUtil.write(content, outFilePath, "UTF-8");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

//    public static void doTidy(String filePath, String outFilePath) throws FileNotFoundException {
//        ByteArrayInputStream stream = new ByteArrayInputStream(filePath.getBytes());
//
//        ByteArrayOutputStream  tidyOutStream = new ByteArrayOutputStream();
//        FileOutputStream  fileOutputStream = new  FileOutputStream(outFilePath);
//        //实例化Tidy对象
//        Tidy tidy = new Tidy();
//        //设置输入
//        tidy.setInputEncoding("gb2312");
//        //如果是true  不输出注释，警告和错误信息
//        tidy.setQuiet(true);
//        //设置输出
//        tidy.setOutputEncoding("gb2312");
//        //不显示警告信息
//        tidy.setShowWarnings(false);
//        //缩进适当的标签内容。
//        tidy.setIndentContent(true);
//        //内容缩进
//        tidy.setSmartIndent(true);
//        tidy.setIndentAttributes(false);
//        //只输出body内部的内容
//        tidy.setPrintBodyOnly(true);
//        //多长换行
//        tidy.setWraplen(1024);
//        //输出为xhtml
//        tidy.setXHTML(true);
//        //去掉没用的标签
//        tidy.setMakeClean(true);
//        //清洗word2000的内容
//        tidy.setWord2000(true);
//        //设置错误输出信息
//        tidy.setErrout(new PrintWriter(System.out));
//        tidy.parse(stream, fileOutputStream);
//    }
//
//    public static void doTidy1(String filePath, String outFilePath) throws FileNotFoundException {
//        //from html to xhtml
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(filePath);
//        } catch (java.io.FileNotFoundException e) {
//            System.out.println("File not found: " + filePath);
//        }
//        Tidy tidy = new Tidy();
//        tidy.setShowWarnings(false);
//        tidy.setXmlTags(false);
//        tidy.setInputEncoding("UTF-8");
//        tidy.setOutputEncoding("UTF-8");
//        tidy.setXHTML(true);//
//        tidy.setMakeClean(true);
//        Document xmlDoc = tidy.parseDOM(fis, null);
//        try
//        {
//            tidy.pprint(xmlDoc, new FileOutputStream(outFilePath));
//        }
//        catch(Exception e)
//        {
//        }
//    }
}
