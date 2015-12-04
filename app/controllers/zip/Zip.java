package controllers.zip;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Iterator;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 12/3/2015
 */
public class Zip {
    /**
     * 解压缩
     * @param warPath 包地址
     * @param unzipPath 解压后地址
     */
    public static void unzip(String warPath, String unzipPath) {
        File warFile = new File(warPath);
        try {
            //获得输出流
            BufferedInputStream bufferedInputStream = new BufferedInputStream(
                    new FileInputStream(warFile));
            ArchiveInputStream in = new ArchiveStreamFactory()
                    .createArchiveInputStream(ArchiveStreamFactory.JAR,
                            bufferedInputStream);
            JarArchiveEntry entry = null;
            //循环遍历解压
            while ((entry = (JarArchiveEntry) in.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    new File(unzipPath, entry.getName()).mkdir();
                } else {
                    OutputStream out = FileUtils.openOutputStream(new File(
                            unzipPath, entry.getName()));
                    IOUtils.copy(in, out);
                    out.close();
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("未找到war文件");
        } catch (ArchiveException e) {
            System.err.println("不支持的压缩格式");
        } catch (IOException e) {
            System.err.println("文件写入发生错误");
        }
    }

    /**
     * 压缩
     * @param destFile 创建的地址及名称
     * @param zipDir 要打包的目录
     */
    public static void zip(String destFile, String zipDir) {
        File outFile = new File(destFile);
        try {
            outFile.createNewFile();
            //创建文件
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(outFile));
            ArchiveOutputStream out = new ArchiveStreamFactory()
                    .createArchiveOutputStream(ArchiveStreamFactory.JAR,
                            bufferedOutputStream);
            if (zipDir.charAt(zipDir.length() - 1) != '/') {
                zipDir += '/';
            }

            Iterator<File> files = FileUtils.iterateFiles(new File(zipDir),
                    null, true);
            File mimetypeFile = null;

            while (files.hasNext()) {
                File file = files.next();
                String name = file.getName();
                if(name.equals("mimetype")){
                    mimetypeFile = file;
                    continue;
                }
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file,
                        file.getPath().replace(zipDir.replace("/", "\\"), ""));
                out.putArchiveEntry(zipArchiveEntry);
                IOUtils.copy(new FileInputStream(file), out);
                out.closeArchiveEntry();
            }

//            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(mimetypeFile,
//                    mimetypeFile.getPath().replace(zipDir.replace("/", "\\"), ""));
//            out.putArchiveEntry(zipArchiveEntry);
//            IOUtils.copy(new FileInputStream(mimetypeFile), out);
//            out.closeArchiveEntry();

            out.finish();
            out.close();
        } catch (IOException e) {
            System.err.println("创建文件失败");
        } catch (ArchiveException e) {
            System.err.println("不支持的压缩格式");
        }
    }
}
