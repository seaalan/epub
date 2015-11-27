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
     * 创建路径
     * @param   folderPath
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean createFolder(String folderPath) {
        File folderFile = new File(folderPath);
        if (folderFile.exists()) {
            return true;
        }
        //Create Folder
        if (folderFile.mkdirs()) {
            return true;
        } else {
            return false;
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

    /**
     * Returns <code>true</code> if the provided String is either <code>null</code>
     * or the empty String <code>""</code>.<p>
     *
     * @param value the value to check
     * @return true, if the provided value is null or the empty String, false otherwise
     */
    public static boolean isEmpty(String value) {
        return (value == null) || (value.length() == 0);
    }

    /**
     * extract file name form the given file path
     *
     * @param fileFullPath      path to the file, like 'c:/test.jpg', 'c:\\test.jpg'
     * @param withExtention indicate contain file.extention. true : contain | false : ignore
     * @return fileName file.name;
     */
    public static String getFileName(String fileFullPath, boolean withExtention) {
        int sep = fileFullPath.lastIndexOf("\\") == -1 ? fileFullPath.lastIndexOf("/") : fileFullPath.lastIndexOf("\\");
        if (withExtention)
            return fileFullPath.substring(sep + 1);
        return fileFullPath.substring(sep + 1, fileFullPath.lastIndexOf("."));
    }

    /**
     * get path to the given file , e.g. : c:\test\aaa.html -> c:\test\
     *
     * @param fileFullPath path to file;
     * @return
     */
    public static String getFilePath(String fileFullPath) {
        int sep = fileFullPath.lastIndexOf("\\") == -1 ? fileFullPath.lastIndexOf("/") : fileFullPath.lastIndexOf("\\");
        return fileFullPath.substring(0, sep + 1);
    }
}
