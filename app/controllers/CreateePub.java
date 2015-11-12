package controllers;

 /**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import nl.siegmann.epublib.chm.ChmParser;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.fileset.FilesetBookCreator;
import nl.siegmann.epublib.util.VFSUtil;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import play.Play;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateePub {

    public static void createePubFromFolder() {
        FilesetBookCreator filesetBookCreator = new FilesetBookCreator();
        String folderPath = Play.application().path()+"/public/book";
        folderPath.replace("/", "//");
        File file = new File(folderPath);
        try {
            //create a ePub from folder
            //Book book = filesetBookCreator.createBookFromDirectory(file);
            Book book = filesetBookCreator.createBookFromDirectory(file,"utf-16 be");
            //output the ePub
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, new FileOutputStream("title3.epub"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createePubFromCHM() {
        ChmParser chmParser = new ChmParser();
        String folderPath = Play.application().path()+"/public/chm/jquery1.7.2.chm";
        folderPath = folderPath.replace("\\", "/");
        folderPath = folderPath.replace("/", "//");
        File file = new File(folderPath);
        FileSystemManager manager;
        try {
            VFSUtil vfsUtil = new VFSUtil();
            FileObject fileObject3 = vfsUtil.resolveFileObject(folderPath);


            manager = VFS.getManager();
            //FileObject fileObject1 = manager.resolveFile(folderPath);
            //FileObject fileObject2 = manager.toFileObject(file);
            Book book = chmParser.parseChm(fileObject3, "utf-16 be");
            //output the ePub
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, new FileOutputStream("title4.epub"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}
