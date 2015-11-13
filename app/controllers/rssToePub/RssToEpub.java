package controllers.rssToePub;

import org.apache.log4j.Logger;

import java.io.File;

public class RssToEpub {
    private static Logger log = Logger.getLogger(RssToEpub.class);

    public static void rssToEpub() {
        String[] args = new String[2];
        args[0] = "D://play//epub//book.yml";
        args[1] = "D://play//epub//book.epub";
        if (args.length != 2) {
            System.out.println("Usage: RssToEpub config_file output_book");
            return;
        }

        File configFile = new File(args[0]);
        if (!configFile.isFile()) {
            log.error("\"" + args[0] + "\" does not exist or is not a file");
            return;
        }

        BookMaker maker = new BookMaker();
        maker.make(args[0], args[1]);
        log.info("Make " + args[1] + " Completed");
    }
}
