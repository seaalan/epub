package controllers.utility;

import controllers.constant.Constant;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/18/2015
 */
public class Txt2XhtmlUtil {

    public static void txt2Xhtml(String content, String outFilePath) {
        try {
            String templateContent = "";
            templateContent = FileUtil.read(Constant.HTML_TEMPLATE_PATH, "UTF-8");

            String[] stringArrary = content.split("<br/>");
            String contentWithManyParagraph = "";
            for (String string : stringArrary) {
                contentWithManyParagraph = contentWithManyParagraph + "<p>" + string + "</p>";
            }

            System.out.print(templateContent);
            // 替换掉模板中相应的地方
            templateContent = templateContent.replaceAll("###title###", FileUtil.getFileName(outFilePath, false));
            templateContent = templateContent.replaceAll("###content###", contentWithManyParagraph);
            templateContent = templateContent.replaceAll("###author###", FileUtil.getFileName(outFilePath, false));
            System.out.print(templateContent);

            FileUtil.createFolder(FileUtil.getFilePath(outFilePath));
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
