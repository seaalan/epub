package controllers.crud;

/**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import controllers.utility.EPubReadWriteUtil;
import models.Epub;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;
import play.data.Form;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EditePub {

    public static void editePub(Form<Epub> ePubForm) {
        EpubReader epubReader = new EpubReader();
        Epub epub = Epub.findById(ePubForm.get().id.toString());

        // read epub file
        Book book = EPubReadWriteUtil.read(epub.url);

        List<String> titlesList = new ArrayList<>();
        List<Author> authorsList = new ArrayList<>();
        List<String> publishersList = new ArrayList<>();

        titlesList.add(ePubForm.get().title);
        authorsList.add(new Author(ePubForm.get().author));
        publishersList.add(ePubForm.get().publisher);

        if (book != null) {
            book.getMetadata().setTitles(titlesList);
            book.getMetadata().setAuthors(authorsList);
            book.getMetadata().setPublishers(publishersList);
        }

        //write epub
        EPubReadWriteUtil.write(book, ePubForm.get().title + ".epub");
    }
}
