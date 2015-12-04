package controllers.EPUBGen;

import com.adobe.dp.epub.io.OCFContainerWriter;
import com.adobe.dp.epub.ncx.TOCEntry;
import com.adobe.dp.epub.opf.NCXResource;
import com.adobe.dp.epub.opf.OPSResource;
import com.adobe.dp.epub.opf.Publication;
import com.adobe.dp.epub.ops.Element;
import com.adobe.dp.epub.ops.OPSDocument;

import java.io.FileOutputStream;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/30/2015
 */
public class HelloEPUB1 {
    /**
     * Simple epubgen example. Basic library use: setting metadata, table of
     * contents, creating chapter content.
     */
    public static void helloEPUB1() {

        try {

            // create new EPUB document
            Publication epub = new Publication();

            // set up title, author and language
            epub.addDCMetadata("title", "My First EPUB");
            epub.addDCMetadata("creator", System.getProperty("user.name"));
            epub.addDCMetadata("language", "en");

            // prepare table of contents
            NCXResource toc = epub.getTOC();
            TOCEntry rootTOCEntry = toc.getRootTOCEntry();

            // create new chapter resource
            OPSResource main = epub.createOPSResource("OPS/main.html");
            epub.addToSpine(main);

            // get chapter document
            OPSDocument mainDoc = main.getDocument();

            // add chapter to the table of contents
            TOCEntry mainTOCEntry = toc.createTOCEntry("Intro", mainDoc
                    .getRootXRef());
            rootTOCEntry.add(mainTOCEntry);

            // chapter XHTML body element
            Element body = mainDoc.getBody();

            // add a header
            Element h1 = mainDoc.createElement("h1");
            h1.add("My First EPUB");
            body.add(h1);

            // add a paragraph
            Element paragraph = mainDoc.createElement("p");
            paragraph.add("Hello, world!");
            body.add(paragraph);

            // save EPUB to an OCF container
            OCFContainerWriter writer = new OCFContainerWriter(
                    new FileOutputStream("D:\\hello.epub"));
            epub.serialize(writer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
