package controllers;

 /**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */
import com.fasterxml.jackson.databind.JsonNode;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import play.Play;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;
import play.libs.Json;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetePub {

    public static List<Map<String,Object>> getePub() {
        EpubReader epubReader = new EpubReader();

        // get IO flow path
        String epubPath = Play.application().path() + "/public/uploads/book5.epub";
        epubPath = epubPath.replace("/", "//");

        // read epub file
        Book book = null;
        try {
            InputStream inputStr = new FileInputStream(epubPath);
            book = epubReader.readEpub(inputStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map<String,Object>> list = new ArrayList<>();
        //放入
        Map<String, Object> map=new HashMap<>();
        if (book != null) {
            map.put("Authors", book.getMetadata().getAuthors());
            map.put("Contributors", book.getMetadata().getContributors());
            map.put("Dates", book.getMetadata().getDates());
            map.put("Descriptions", book.getMetadata().getDescriptions());
            map.put("FirstTitle", book.getMetadata().getFirstTitle());
            map.put("Format", book.getMetadata().getFormat() );
            map.put("Identifiers", book.getMetadata().getIdentifiers());
            map.put("Language", book.getMetadata().getLanguage());
    //        map.put("MetaAttribute", book.getMetadata().getMetaAttribute(String name) );
            map.put("Publishers", book.getMetadata().getPublishers());
            map.put("Rights", book.getMetadata().getRights());
            map.put("Subjects", book.getMetadata().getSubjects());
            map.put("Titles", book.getMetadata().getTitles());
            map.put("Types", book.getMetadata().getTypes());
            list.add(map);
        }
        for (Map<String, Object> aList : list) {
            for (Author author : (List<Author>) aList.get("Authors")) System.out.println("author:" + author.toString());
            for (Author contributor : (List<Author>) aList.get("Contributors")) System.out.println("contributor:" + contributor.toString());
            for (Date thedate : (List<Date>) aList.get("Dates")) System.out.println("thedate:" + thedate.toString());
            for (String description : (List<String>) aList.get("Descriptions")) System.out.println("description:" + description);
            System.out.println("FirstTitle:" + aList.get("FirstTitle"));
            System.out.println("Format:" + aList.get("Format"));
            for (Identifier identifier : (List<Identifier>) aList.get("Identifiers")) System.out.println("identifier:" + identifier.toString());
            System.out.println("Language:" + aList.get("Language"));
            for (String publisher : (List<String>) aList.get("Publishers")) System.out.println("publisher:" + publisher);
            for (String right : (List<String>) aList.get("Rights")) System.out.println("right:" + right);
            for (String subject : (List<String>) aList.get("Subjects")) System.out.println("subject:" + subject);
            for (String title : (List<String>) aList.get("Titles")) System.out.println("title:" + title);
            for (String type : (List<String>) aList.get("Types")) System.out.println("type:" + type);
        }
        JsonNode ii = Json.toJson(list);
        System.out.println("Json:"+ii);
        return list;
    }
}
