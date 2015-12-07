package controllers.convert;

import controllers.utility.FileUtil;
import controllers.zip.Zip;
import controllers.zip.ZipTools;

import java.io.IOException;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 12/7/2015
 */
public class Epub2ToEpub3 {
    public static void epub2ToEpub3(String filePath, String outFilePath) throws IOException {
        //1.解压Epub2文件
        Zip.unzip("D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee.epub", "D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee\\");
        //2.修改Epub2相关文件
        //2.1修改content.opf文件
        String basePath = "D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee\\";
        try {
            filePath = basePath + "OEBPS\\content.opf";
            outFilePath = filePath;

            String content = FileUtil.read(filePath, "UTF-8");
            String packageString = "<opf:package version=\"2.0\" unique-identifier=\"BookId\" xmlns:opf=\"http://www.idpf.org/2007/opf\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">";
            String replacePackageString = "<package version=\"3.0\" unique-identifier=\"BookId\" xmlns=\"http://www.idpf.org/2007/opf\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">";
            String meta = "<meta property=\"dcterms:modified\">2011-01-01T12:00:00Z</meta></metadata>";
            String item = "<manifest><item id=\"nav\" href=\"nav.xhtml\" properties=\"nav\" media-type=\"application/xhtml+xml\"/>";
            String identifier = "<dc:identifier id=\"BookId\" scheme=\"UUID\">";
            String creator = "<dc:creator role=\"aut\" file-as=\"alex, \">";

            // 替换掉模板中相应的地方
            content = content.replaceAll(packageString, replacePackageString);
            content = content.replaceAll("opf:", "");
            content = content.replaceAll("</metadata>", meta);
            content = content.replaceAll("<manifest>", item);
            content = content.replaceAll(identifier, "<dc:identifier id=\"BookId\">");
            content = content.replaceAll(creator, "<dc:creator>");

            FileUtil.createFolder(FileUtil.getFilePath(outFilePath));
            FileUtil.write(content, outFilePath, "UTF-8");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        //2.2修改eee.xhtml文件
        try {
            filePath = basePath + "OEBPS\\eee.xhtml";
            outFilePath = filePath;

            String content = FileUtil.read(filePath, "UTF-8");
            String img = "width=\"225.0pt\" height=\"167.25pt\"";
            String replaceImg = "style=\"width:225.0pt; height:167.25pt\"";

            // 替换掉模板中相应的地方
            content = content.replaceAll(img, replaceImg);

            FileUtil.createFolder(FileUtil.getFilePath(outFilePath));
            FileUtil.write(content, outFilePath, "UTF-8");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        //3.增加Epub3需要的相关文件
        String navContent = FileUtil.read("D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\nav.xhtml", "UTF-8");
        FileUtil.write(navContent, basePath + "OEBPS\\nav.xhtml", "UTF-8");
        //4.重新打包成Epub3文件
        try {
            ZipTools.zipFile("D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee", "D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee\\eee1.epub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
