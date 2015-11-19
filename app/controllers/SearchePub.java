package controllers;

/**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.search.SearchIndex;
import nl.siegmann.epublib.domain.Author;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SearchePub {

    public static void searchePub() {
        EpubReader epubReader = new EpubReader();
        String url = "D:\\play\\epub\\1.epub";
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

        SearchIndex searchIndex = new SearchIndex(book);
        Book book1 = searchIndex.getBook();
        book.getTitle();
        System.out.print(book.getTitle());
    }

    public static void testePub() {
        Author author = new Author("sea", "alan");
        System.out.println("Firstname:");
        System.out.println("Firstname:" + author.getFirstname());
        System.out.println("Lastname:" + author.getLastname());
        System.out.println("Relator Name:" + author.getRelator().getName());
        System.out.println("Relator Code:" + author.getRelator().getCode());
    }
}
