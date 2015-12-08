package controllers.convert;

import controllers.utility.FileUtil;
import controllers.zip.Zip;
import controllers.zip.ZipTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        filePath = basePath + "OEBPS\\content.opf";

        try {
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

    public static void epub2ToEpub31(String filePath, String outFilePath) throws IOException {
        //1.解压Epub2文件
        Zip.unzip("D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee.epub", "D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee\\");
        //2.修改Epub2相关文件
        //2.1修改content.opf文件
        String basePath = "D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee\\";
        filePath = basePath + "OEBPS\\content.opf";
        outFilePath = filePath;
        //逐行读取
        List<String> readAsLines = FileUtil.readAsLines(filePath, "UTF-8");
        //替换内容
        String packageString = "";
        String identifier = "";
        String creator = "";
        boolean hasmeta = true;
        for(int i=0; i<readAsLines.size(); i++){
            int index_space_package = readAsLines.get(i).indexOf("<opf:package");
            if(index_space_package != -1){
                int index_signe_package = readAsLines.get(i).indexOf('>');
                packageString = readAsLines.get(i).substring(index_space_package, index_signe_package + 1);
                readAsLines.set(i,readAsLines.get(i).replaceAll(packageString, "<package version=\"3.0\" unique-identifier=\"BookId\" xmlns=\"http://www.idpf.org/2007/opf\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">"));
            }
            int index_space_identifier = readAsLines.get(i).indexOf("<dc:identifier");
            if(index_space_identifier != -1){
                int index_signe_identifier = readAsLines.get(i).indexOf('>');
                identifier = readAsLines.get(i).substring(index_space_identifier, index_signe_identifier + 1);

                int index_space_identifier_id = readAsLines.get(i).indexOf("id=");
                int index_signe_identifier_id = readAsLines.get(i).indexOf("opf:");
                String identifier_id = readAsLines.get(i).substring(index_space_identifier_id, index_signe_identifier_id - 1);
                readAsLines.set(i,readAsLines.get(i).replaceAll(identifier, "<dc:identifier " + identifier_id + ">"));
            }
            int index_space_creator = readAsLines.get(i).indexOf("<dc:creator");
            if(index_space_creator != -1){
                int index_signe_creator = readAsLines.get(i).indexOf('>');
                creator = readAsLines.get(i).substring(index_space_creator, index_signe_creator + 1);
                readAsLines.set(i,readAsLines.get(i).replaceAll(creator, "<dc:creator>"));
            }
            readAsLines.set(i,readAsLines.get(i).replaceAll("opf:", ""));

            int index_space_metadata = readAsLines.get(i).indexOf("</metadata>");
            if(index_space_metadata != -1 && hasmeta){
                readAsLines.add(i, "    <meta property=\"dcterms:modified\">2011-01-01T12:00:00Z</meta>");
                hasmeta = false;
            }
            int index_space_manifest = readAsLines.get(i).indexOf("<manifest>");
            if(index_space_manifest != -1){
                readAsLines.add(i + 1, "    <item id=\"nav\" href=\"nav.xhtml\" properties=\"nav\" media-type=\"application/xhtml+xml\"/>");
            }
        }
        //逐行写入
        FileUtil.writeByLine(outFilePath, readAsLines);
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
        //首先读取toc.ncx文件内容,提取目录内容
        List<String> readTocAsLines = FileUtil.readAsLines(basePath + "OEBPS\\toc.ncx", "UTF-8");
        List<String> srcList = new ArrayList<>();
        for(int i=0; i<readTocAsLines.size(); i++){
            int index_space_content = readTocAsLines.get(i).indexOf("<content");
            if(index_space_content != -1){
                int index_space_src = readTocAsLines.get(i).indexOf("src=\"");
                int index_signe_src = readTocAsLines.get(i).indexOf("/>");
                String srcString = readTocAsLines.get(i).substring(index_space_src+5, index_signe_src-2);
                srcList.add(srcString);
            }
        }
        //组装xhtml中li的目录内容
        String liContent = "";
        for(String src : srcList){
            liContent = liContent + "<li><a href=\"" + src + "\">" + src + "</a></li>\n";
        }
        //将提取的目录内容添加到nav.xhtml模板中
        String navContent = FileUtil.read("D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\navnew.xhtml", "UTF-8");
        navContent = navContent.replaceAll("<ol></ol>", "<ol>\n"+liContent+"</ol>");
        FileUtil.write(navContent, basePath + "OEBPS\\nav.xhtml", "UTF-8");
        //4.重新打包成Epub3文件
        try {
            ZipTools.zipFile("D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee", "D:\\play\\epub\\public\\epub\\Epub2ToEpub3\\eee\\eee1.epub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
