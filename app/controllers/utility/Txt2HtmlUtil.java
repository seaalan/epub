package controllers.utility;

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
            templateContent = FileUtil.read(htmlTemplatePath, "UTF-8");

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
            FileUtil.write(templateContent, outFilePath, "GBK");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
