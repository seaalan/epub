package controllers.data;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import models.Epub;
import nl.siegmann.epublib.domain.Book;
import play.data.Form;
import play.mvc.Controller;

import java.util.List;

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

    public static List<Epub> searchePub(Form<Epub> ePubForm) {
        List<Epub> epubList = Epub.find.where()
                                .eq("title", ePubForm.get().title)
                                .eq("author", ePubForm.get().author)
                                .eq("publisher", ePubForm.get().publisher)
                                .findList();
        //Example: Create the query using the API.
        List<Epub> epubList1 =
                Ebean.find(Epub.class)
                        //.fetch("title")
                        .where()
                        .like("title",ePubForm.get().title+"%")
                        .orderBy("id desc")
                        .setMaxRows(10)
                        .findList();
        //Example: The same query using the query language
        String oql =    " find epub "
                        //+" fetch title "
                        +" where title like :title ";
        if(ePubForm.get().author != null && ePubForm.get().author != ""){
            oql = oql + " and author = :author ";
        }
        if(ePubForm.get().publisher != null && ePubForm.get().publisher != ""){
            oql = oql + " and publisher = :publisher ";
        }
        oql = oql + " limit 10 ";
        Query<Epub> query = Ebean.createQuery(Epub.class, oql);
        query.setParameter("title", ePubForm.get().title+"%");
        if(ePubForm.get().author != null && ePubForm.get().author != ""){
            query.setParameter("author", ePubForm.get().author);
        }
        if(ePubForm.get().publisher != null && ePubForm.get().publisher != ""){
            query.setParameter("publisher", ePubForm.get().publisher);
        }
        List<Epub> epubList2 = query.findList();
        //Example: Using a named query called "with.cust.and.details"
//        Query<Epub> query = Ebean.createNamedQuery(Epub.class,"with.cust.and.details");
//        query.setParameter("title",ePubForm.get().title+"%");
//        List<Epub> epubList3 = query.findList();

        return epubList2;
    }
}