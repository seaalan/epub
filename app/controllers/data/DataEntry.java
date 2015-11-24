package controllers.data;

import models.Epub;
import nl.siegmann.epublib.domain.Book;
import play.data.Form;
import play.mvc.Controller;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/24/2015
 */
public class DataEntry extends Controller{

    public static void updateePub(Form<Epub> ePubForm) {
        Epub epub = Epub.findById(ePubForm.get().id.toString());
        epub.title = ePubForm.get().title;
        epub.author = ePubForm.get().author;
        epub.publisher = ePubForm.get().publisher;
        epub.update();
    }

    public static void saveePub(Book book, String filePath) {
        Epub epub = new Epub();
        epub.title = book.getMetadata().getTitles().get(0);
        epub.author =  book.getMetadata().getAuthors().get(0).toString();
        epub.publisher = book.getMetadata().getPublishers().get(0);
        epub.url = filePath;
        epub.save();
    }
}