package controllers;

/**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import controllers.data.DataEntry;
import models.Epub;
import nl.siegmann.epublib.domain.Author;
import play.data.Form;

import java.util.List;

public class SearchePub {

    public static List<Epub> searchePub(Form<Epub> ePubForm) {
        return DataEntry.searchePub(ePubForm);
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
