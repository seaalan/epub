package controllers;

/**
 * Copyright 2015 Erealm Info & Tech.
 *
 * Created by alex on 11/6/2015
 */

import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubWriter;
import org.apache.poi.hpsf.SummaryInformation;
import play.Play;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddePub {
    private static InputStream getResource(String path) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File(Play.application().path(), path));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return stream;
    }

    private static Resource getResource(String path, String href) throws IOException {
        return new Resource(getResource(path), href);
    }

    public static Book addePub(String title) {
        try {
            // Create new Book
            Book book = new Book();
            Metadata metadata = book.getMetadata();

            // Add the title
            metadata.addTitle(title);
            // Add an Author
            metadata.addAuthor(new Author("Joe", "Tester"));
            metadata.addAuthor(new Author("Joe1", "Tester1"));
            // Add an Contributor
            metadata.addContributor(new Author("Sea", "Alan"));
            // Add Date
            metadata.addDate(new Date(new java.util.Date()));
            // Add Description
            metadata.addDescription("This is a test ePub.");
            metadata.addDescription("This is a test ePub1.");
            metadata.addDescription("This is a test ePub2.");
            // Add Identifier (A Book's identifier. Defaults to a random UUID and scheme "UUID")
            metadata.addIdentifier(new Identifier());
            // Add Publisher
            metadata.addPublisher("Youth Publishing House");
            // Add Type
            metadata.addType("doc");

            // Set cover image
            book.setCoverImage(
                    getResource("/public/book/cover.png", "cover.png"));
            // Set cover page
            book.setCoverPage(
                    getResource("/public/book/cover.html", "cover.html"));

            // Add Chapter 1
            TOCReference chapter1 =
                    book.addSection("Introduction",
                            getResource("/public/book/chapter1.html", "chapter1.html"));
            // Add css file
            book.getResources().add(
                    getResource("/public/book/book1.css", "book1.css"));

            // Add Chapter 2
            TOCReference chapter2 =
                    book.addSection("Second Chapter",
                            getResource("/public/book/chapter2.html", "chapter2.html"));
            // Add image used by Chapter 2
            book.getResources().add(
                    getResource("/public/book/flowers_320x240.jpg", "flowers.jpg"));
            // Add Chapter2, Section 1
            book.addSection(chapter2, "Chapter 2, section 1",
                    getResource("/public/book/chapter2_1.html", "chapter2_1.html"));

            // Add Chapter 3
            TOCReference chapter3 =
                    book.addSection("Conclusion",
                            getResource("/public/book/chapter3.html", "chapter3.html"));

            List<TOCReference> tocReferences = new ArrayList<>();
            tocReferences.add(chapter1);
            tocReferences.add(chapter2);
            tocReferences.add(chapter3);
            TableOfContents tableOfContents = new TableOfContents(tocReferences);
            // Set TableOfContents
            book.setTableOfContents(tableOfContents);
            // Set Spine
            book.setSpine(new Spine(tableOfContents));

            // Create EpubWriter
            EpubWriter epubWriter = new EpubWriter();

            // Write the Book as Epub
            epubWriter.write(book, new FileOutputStream(title + ".epub"));
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Book addePub(SummaryInformation summaryInformation, String text) {
        try {
            // Create new Book
            Book book = new Book();
            Metadata metadata = book.getMetadata();

            // Add the title
            metadata.addTitle(summaryInformation.getTitle());
            // Add an Author
            metadata.addAuthor(new Author(summaryInformation.getAuthor()));
            // Add Date
            metadata.addDate(new Date(new java.util.Date()));
            // Add Identifier (A Book's identifier. Defaults to a random UUID and scheme "UUID")
            metadata.addIdentifier(new Identifier());
            // Add Type
            metadata.addType("doc");

            TOCReference chapter1 =
                    book.addSection("chapter1",
                            new Resource(new ByteArrayInputStream(text.getBytes()), "chapter1.html")
                    );

            List<TOCReference> tocReferences = new ArrayList<>();
            tocReferences.add(chapter1);
            TableOfContents tableOfContents = new TableOfContents(tocReferences);
            // Set TableOfContents
            book.setTableOfContents(tableOfContents);
            // Set Spine
            book.setSpine(new Spine(tableOfContents));

            // Create EpubWriter
            EpubWriter epubWriter = new EpubWriter();

            // Write the Book as Epub
            epubWriter.write(book, new FileOutputStream(summaryInformation.getTitle() + ".epub"));
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Book addePub(File file) {
        try {
            // Create new Book
            Book book = new Book();
            Metadata metadata = book.getMetadata();

            // Add the title
            metadata.addTitle("txt");
            // Add an Author
            metadata.addAuthor(new Author("txt"));
            // Add Date
            metadata.addDate(new Date(new java.util.Date()));
            // Add Identifier (A Book's identifier. Defaults to a random UUID and scheme "UUID")
            metadata.addIdentifier(new Identifier());
            // Add Type
            metadata.addType("doc");


            String encoding = "GBK";
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                List<TOCReference> tocReferences = new ArrayList<>();
                int i = 1;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt.length() == 0) continue;
                    TOCReference chapter =
                            book.addSection("chapter" + i,
                                    new Resource(new ByteArrayInputStream(lineTxt.getBytes()), "chapter" + i + ".html")
                            );
                    tocReferences.add(chapter);
                    i++;
                }
                TableOfContents tableOfContents = new TableOfContents(tocReferences);
                // Set TableOfContents
                book.setTableOfContents(tableOfContents);
                // Set Spine
                book.setSpine(new Spine(tableOfContents));
                read.close();
            }


//            TOCReference chapter1 =
//                    book.addSection("chapter1",
//                            new Resource( new FileInputStream(file), "chapter1.html" )
//                    );
//
//            List<TOCReference> tocReferences = new ArrayList<>();
//            tocReferences.add(chapter1);
//            TableOfContents tableOfContents = new TableOfContents(tocReferences);
//            // Set TableOfContents
//            book.setTableOfContents(tableOfContents);
//            // Set Spine
//            book.setSpine(new Spine(tableOfContents));

            // Create EpubWriter
            EpubWriter epubWriter = new EpubWriter();

            // Write the Book as Epub
            epubWriter.write(book, new FileOutputStream("txt" + ".epub"));
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
