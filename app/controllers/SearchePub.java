package controllers;

 /**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.fileset.FilesetBookCreator;
import play.Play;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SearchePub {

    public static void searchePub() {
        FilesetBookCreator filesetBookCreator = new FilesetBookCreator();
        String s = Play.application().path()+"/public/book";
        s.replace("/", "//");
        File file = new File( s );
        try {
            Book book = filesetBookCreator.createBookFromDirectory(file);
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, new FileOutputStream("title.epub"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
