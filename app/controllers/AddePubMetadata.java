package controllers;

/**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import controllers.data.DataEntry;
import controllers.utility.EPubReadWriteUtil;
import controllers.utility.FileUtil;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.epub.EpubReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddePubMetadata {

    public static void addePubMetadata(String filePath, Map metadata) throws Exception {

        EpubReader epubReader = new EpubReader();
        // read epub file
        Book book = EPubReadWriteUtil.read(filePath);

        List<String> titlesList = new ArrayList<>();
        List<Author> authorsList = new ArrayList<>();
        List<String> publishersList = new ArrayList<>();
        List<Date> datesList = new ArrayList<>();

        titlesList.add(FileUtil.getFileName(filePath, false));
        authorsList.add(new Author(metadata.get("author").toString()));
        publishersList.add(metadata.get("publisher").toString());
        datesList.add(new Date(new java.util.Date()));

        if (book != null) {
            book.getMetadata().setTitles(titlesList);
            book.getMetadata().setAuthors(authorsList);
            book.getMetadata().setPublishers(publishersList);
            book.getMetadata().setDates(datesList);
        }

        // write epub file
        EPubReadWriteUtil.write(book, filePath);

        DataEntry.saveePub(book, filePath);
    }
}
