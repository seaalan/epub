package controllers.utility;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;

import java.io.*;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/24/2015
 */
public class EPubReadWriteUtil {

    public static Book read(String filePath) {
        EpubReader epubReader = new EpubReader();

        // read epub file
        Book book = null;
        try {
            InputStream inputStr = new FileInputStream(filePath);
            book = epubReader.readEpub(inputStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    public static void write(Book book, String outFilePath) {
        EpubWriter epubWriter = new EpubWriter();
        try {
            OutputStream ouput = new FileOutputStream(outFilePath);
            epubWriter.write(book, ouput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
