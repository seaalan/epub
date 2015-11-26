package controllers.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/20/2015
 */
public class FileUtil {

    private static final String SEP = System.getProperty("line.separator");

    public static String read(String fileName, String encoding) {
        StringBuffer sb = new StringBuffer();
        try {
//            String templateContent = "";
//            FileInputStream fileinputstream = new FileInputStream(htmlTemplatePath);// 读取模板文件
//            int lenght = fileinputstream.available();
//            byte bytes[] = new byte[lenght];
//            fileinputstream.read(new byte[fileinputstream.available()]);
//            fileinputstream.close();
//            templateContent = new String(bytes);
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, encoding);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(SEP);
            }
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static List<String> readAsLines(String fileName, String encoding) {
        List<String> lines = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, encoding);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }


    public static void write(String fileContent, String outFilePath, String encoding) {
        try {

//            FileOutputStream fileoutputstream = new FileOutputStream(outFilePath);// 建立文件输出流
//            byte tag_bytes[] = templateContent.getBytes("GBK");//读取文件时指定字符编码
//            fileoutputstream.write(tag_bytes);
//            fileoutputstream.close();
            FileOutputStream fos = new FileOutputStream(outFilePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
            osw.write(fileContent);
            osw.flush();
            osw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
