package controllers.crud;

/**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.fileset.FilesetBookCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class CreateePub {
    private final static String createePubFolderPath = "D:\\play\\epub\\public\\12";
    private final static String ePubOutPath = "12.epub";

    public static void createePubFromFolder() {
        FilesetBookCreator filesetBookCreator = new FilesetBookCreator();
//        String folderPath = Play.application().path()+"/public/book";
//        folderPath = folderPath.replace("/", "//");
        File file = new File(createePubFolderPath);
        try {
            //create a ePub from folder
            //Book book = filesetBookCreator.createBookFromDirectory(file);
            Book book = filesetBookCreator.createBookFromDirectory(file, "utf-16 be");
            //output the ePub
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, new FileOutputStream(ePubOutPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createePubFromFolder(String folderPath, String outFilePath, Map metadata) {
        FilesetBookCreator filesetBookCreator = new FilesetBookCreator();
        File file = new File(folderPath);
        try {
            //create a ePub from folder
            Book book = filesetBookCreator.createBookFromDirectory(file, "utf-16 be");
            //output the ePub
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, new FileOutputStream(outFilePath));
            AddePubMetadata.addePubMetadata(outFilePath, metadata);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void createePubFromCHM() {
//        ChmParser chmParser = new ChmParser();
//        String folderPath = Constant.ROOT_PATH + "/htmlhelp.zip";
//        folderPath = folderPath.replace("\\", "/");
//        folderPath = folderPath.replace("/", "/");
//        File file = new File(folderPath);
//        FileSystemManager manager;
//        try {
////            VFSUtil vfsUtil = new VFSUtil();
////            FileObject fileObject3 = vfsUtil.resolveFileObject(folderPath);
//
//
//            manager = VFS.getManager();
//            FileObject fileObject1 = manager.toFileObject(file);
//
//            //FileObject fileObject2 = manager.toFileObject(file);
//            Book book = chmParser.parseChm(fileObject1);
//            //output the ePub
//            EpubWriter epubWriter = new EpubWriter();
//            epubWriter.write(book, new FileOutputStream("title4.epub"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
