package controllers.rssToePub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubWriter;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class BookMaker {
    private Logger log = Logger.getLogger(BookMaker.class);

    /**
     * Parse configuration file
     * 
     * @param configFilePath
     *            Configuration file path
     * @return
     */
    protected BookConfig parseConfig(String configFilePath) {
        Yaml yaml = new Yaml(new Constructor(BookConfig.class));
        BookConfig config;
        try {
            File file = new File(configFilePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            Object object = yaml.load(fileInputStream);
            config = (BookConfig) object;
        } catch (FileNotFoundException e) {
            log.error("Configuration file does not exist");
            return null;
        }

        log.info("Configuration loaded");
        return config;
    }

    /**
     * Read content from feeds
     * 
     * @param config
     *            Configuration
     * @return
     */
    protected ArrayList<SyndFeed> readFeeds(BookConfig config) {
        ArrayList<SyndFeed> feeds = new ArrayList<SyndFeed>();

        for (String feedUrl : config.getFeeds()) {
            try {
                URL url = new URL(feedUrl);
                XmlReader reader = new XmlReader(url);
                SyndFeed feed = new SyndFeedInput().build(reader);
                feeds.add(feed);
                log.info("Read rss source \"" + feedUrl + "\" Success");
            } catch (Exception e) {
                log.warn("Read rss source \"" + feedUrl + "\" Failed");
                continue;
            }
        }

        log.info("Read rss completed");
        return feeds;
    }

    /**
     * Make epub book
     * 
     * @param config
     *            Configuration
     * @param feeds
     *            Feeds content
     * @param outputFilePath
     *            Output epub book path
     */
    public void make(String configFilePath, String outputFilePath) {
        BookConfig config = this.parseConfig(configFilePath);
        ArrayList<SyndFeed> feeds = this.readFeeds(config);
        Pattern pattern = Pattern
                .compile("src=\"(http[s]?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]+)\\.(png|jpg|jpeg|gif|svg)(\\??[^\".]*)\"");

        Book book = new Book();
        book.getMetadata().addTitle(config.getTitle());
        book.getMetadata().addAuthor(new Author(config.getAuthor()));
        book.getMetadata().setLanguage("zh-CN");
        try {
            book.setCoverImage(new Resource(new FileInputStream(config
                    .getCover()), "cover.jpg"));
        } catch (IOException e) {
            log.warn("Set cover image Failed");
        }

        int feedNum = 1;
        for (SyndFeed feed : feeds) {
            StringBuilder sb = new StringBuilder();
            sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            sb.append("<head>");
            sb.append("<title>");
            sb.append(feed.getTitle());
            sb.append("</title>");
            sb.append("</head>");
            sb.append("<body>");
            sb.append("<h1>");
            sb.append(feed.getTitle());
            sb.append("</h1>");
            if (feed.getAuthor() != null) {
                sb.append("<p>");
                sb.append(feed.getAuthor());
                sb.append("</p>");
            }
            if (feed.getLink() != null) {
                sb.append("<p>");
                sb.append(feed.getLink());
                sb.append("</p>");
            }
            sb.append("</body>");
            sb.append("</html>");
            String href = "feed" + feedNum + ".html";
            TOCReference site;
            try {
                site = book.addSection(feed.getTitle(), new Resource(href, sb
                        .toString().getBytes("UTF-8"), href, new MediaType(
                        "application/xhtml+xml", ".html")));
            } catch (UnsupportedEncodingException e) {
                log.warn("Get \"" + feed.getLink() + "\" Failed");
                continue;
            }

            @SuppressWarnings("rawtypes")
            Iterator iter = feed.getEntries().iterator();

            int articleNum = 1;
            while (iter.hasNext()) {
                SyndEntry entry = (SyndEntry) iter.next();

                StringBuilder sb2 = new StringBuilder();
                sb2.append("<h2>");
                sb2.append(entry.getTitle());
                sb2.append("</h2>");
                if (entry.getAuthor() != null) {
                    sb2.append("<p>");
                    sb2.append(entry.getAuthor());
                    sb2.append("</p>");
                }
                if (entry.getPublishedDate() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sb2.append("<p>");
                    sb2.append(df.format(entry.getPublishedDate()));
                    sb2.append("</p>");
                }
                if (entry.getLink() != null) {
                    sb2.append("<p>");
                    sb2.append(entry.getLink());
                    sb2.append("</p>");
                }
                sb2.append("<div>");
                if (!entry.getContents().isEmpty()) {
                    sb2.append(((SyndContent) (entry.getContents().get(0)))
                            .getValue());
                } else {
                    sb2.append(entry.getDescription().getValue());
                }
                sb2.append("</div>");
                String body = sb2.toString();

                if (config.isImage()) {
                    Matcher matcher = pattern.matcher(body);
                    int imageNum = 1;
                    while (matcher.find()) {
                        URL url;
                        String urlString = matcher.group(1) + "."
                                + matcher.group(2);
                        try {
                            url = new URL(urlString);
                        } catch (MalformedURLException e) {
                            log.warn("Get image \"" + urlString + "\" Failed");
                            continue;
                        }
                        InputStream is;
                        try {
                            is = url.openStream();
                        } catch (IOException e) {
                            log.warn("Get image \"" + urlString + "\" Failed");
                            continue;
                        }
                        String imageHref = "feed" + feedNum + "-article"
                                + articleNum + "-image" + imageNum + "."
                                + matcher.group(2);
                        body = body.replace(urlString + matcher.group(3),
                                imageHref);
                        try {
                            book.addResource(new Resource(is, imageHref));
                        } catch (IOException e) {
                            log.warn("Get image \"" + urlString + "\" Failed");
                            continue;
                        }

                        imageNum++;
                        log.info("Get image \"" + urlString + "\" Success");
                    }
                }

                String href2 = "feed" + feedNum + "-article" + articleNum
                        + ".html";
                StringBuilder sb3 = new StringBuilder();
                sb3.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
                sb3.append("<head>");
                sb3.append("<title>");
                sb3.append(entry.getTitle());
                sb3.append("</title>");
                sb3.append("</head>");
                sb3.append("<body>");
                sb3.append(body);
                sb3.append("</body>");
                sb3.append("</html>");
                try {
                    book.addSection(site, entry.getTitle(), new Resource(href2,
                            sb3.toString().getBytes("UTF-8"), href2,
                            new MediaType("application/xhtml+xml", ".html")));
                } catch (UnsupportedEncodingException e) {
                    log.warn("Get \"" + entry.getLink() + "\" Failed");
                    continue;
                }

                articleNum++;
                log.info("Get \"" + entry.getLink() + "\" Success");
            }

            feedNum++;
        }

        EpubWriter epub = new EpubWriter();
        try {
            epub.write(book, new FileOutputStream(outputFilePath));
        } catch (IOException e) {
            return;
        }
    }
}
