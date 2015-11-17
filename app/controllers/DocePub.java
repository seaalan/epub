package controllers;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.*;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/16/2015
 */
public class DocePub {
    public static void docePub(File f)throws IOException {
        FileInputStream fis = new FileInputStream(f);
        HWPFDocument doc = new HWPFDocument(fis);
        Range rang = doc.getRange();
        String text = rang.text().toString();
        SummaryInformation summaryInformation = doc.getSummaryInformation();
//        String Title = summaryInformation.getTitle();
//        String Author = summaryInformation.getAuthor();
        fis.close();
        AddePub.addePub(summaryInformation, text);
    }


    /**
     * 回车符ASCII码
     */
    private static final short ENTER_ASCII = 13;

    /**
     * 空格符ASCII码
     */
    private static final short SPACE_ASCII = 32;

    /**
     * 水平制表符ASCII码
     */
    private static final short TABULATION_ASCII = 9;

    private static String htmlText = "";

    /**
     * 读取每个文字样式
     *
     * @param fileName
     * @throws Exception
     */
    public static void docToHtml(String fileName) throws Exception {

        FileInputStream in = new FileInputStream(new File(fileName));
        InputStreamReader read = new InputStreamReader(in, "GBK");//考虑到编码格式

        HWPFDocument doc = new HWPFDocument(in);

        // 取得文档中字符的总数
        int length = doc.characterLength();

        // 创建图片容器
        PicturesTable pTable = doc.getPicturesTable();

        htmlText = "<html><head><title>" + doc.getSummaryInformation().getTitle() + "</title></head><body>";

        // 创建临时字符串,好加以判断一串字符是否存在相同格式

        String tempString = "";

        for (int i = 0; i < length - 1; i++) {
            // 整篇文章的字符通过一个个字符的来判断,range为得到文档的范围
            Range range = new Range(i, i + 1, doc);

            CharacterRun cr = range.getCharacterRun(0);

            if (pTable.hasPicture(cr)) {

                // 读写图片
                readPicture(pTable, cr);

            } else {

                Range range2 = new Range(i + 1, i + 2, doc);

                // 第二个字符
                CharacterRun cr2 = range2.getCharacterRun(0);

                // 当前字符
                char currentChar = cr.text().charAt(0);

                // 判断是否为回车符
                if (currentChar == ENTER_ASCII)
                    tempString += "<br/>";
                    // 判断是否为空格符
                else if (currentChar == SPACE_ASCII)
                    tempString += "&nbsp;";
                    // 判断是否为水平制表符
                else if (currentChar == TABULATION_ASCII)
                    tempString += " &nbsp;&nbsp;&nbsp;";
                // 比较前后2个字符是否具有相同的格式
                boolean flag = compareCharStyle(cr, cr2);

                String fontStyle = "<span style='font-family:" + cr.getFontName() + ";font-size:" + cr.getFontSize() / 2 + "pt;";

                if (cr.isBold())
                    fontStyle += "font-weight:bold;";
                if (cr.isItalic())
                    fontStyle += "font-style:italic;";

                if (flag && i != length - 2)
                    tempString += currentChar;
                else if (!flag) {
                    htmlText += fontStyle + "'>" + tempString + currentChar + "</span>";
                    tempString = "";
                } else
                    htmlText += fontStyle + "'>" + tempString + currentChar + "</span>";
            }
        }
        htmlText += "</body></html>";

        writeFile(htmlText);
    }

        /**
         * 读写文档中的图片
         *
         * @param pTable
         * @param cr
         * @throws Exception
         */
    private static void readPicture(PicturesTable pTable, CharacterRun cr) throws Exception {
        // 提取图片
        Picture pic = pTable.extractPicture(cr, false);

        // 返回POI建议的图片文件名
        String afileName = pic.suggestFullFileName();

        OutputStream out = new FileOutputStream(new File("d:\\" + File.separator + afileName));

        pic.writeImageContent(out);

        htmlText += "<img src='d:\\" + afileName + "'/>";
    }

    private static boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) {
        boolean flag = false;
        if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic() && cr1.getFontName().equals(cr2.getFontName()) && cr1.getFontSize() == cr2.getFontSize()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 写文件
     *
     * @param s
     */
    private static void writeFile(String s) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File("d:\\abcd2.html");
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(s);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }
    }
}
