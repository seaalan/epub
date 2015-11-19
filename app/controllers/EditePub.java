package controllers;

/**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EditePub {

    public static void editePub(String title, String url) {
        EpubReader epubReader = new EpubReader();

        // get IO flow path
//        String currentPath = Thread.currentThread().getClass().getResource("/").toString();
//        System.out.println(currentPath);
//        String epubPath = currentPath + "epub/myBook.epub";
//        System.out.println(epubPath);
//        epubPath = epubPath.substring(6, epubPath.length());
//        System.out.println(epubPath);
//        epubPath = epubPath.replace("/", "//");
//        System.out.println(epubPath);

//        String epubPath = Play.application().path() + "/public/uploads/test1_book8.epub";
//        epubPath = epubPath.replace("/", "//");

        String epubPath = url.replace("/", "//");
        System.out.println("epubPath:" + epubPath);
        // read epub file
        Book book = null;
        try {
            InputStream inputStr = new FileInputStream(epubPath);
            book = epubReader.readEpub(inputStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set epub file title.
        // change toc.ncx file's <docTitle> and content.opf file's <dc:title>.
        List<String> titlesList = new ArrayList<>();
        titlesList.add(title);
        if (book != null) {
            book.getMetadata().setTitles(titlesList);
        }

        //write epub
        EpubWriter epubWriter = new EpubWriter();
        try {
            OutputStream ouput = new FileOutputStream(title + ".epub");
            epubWriter.write(book, ouput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
