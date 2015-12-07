package controllers.EPUBCheck;

import com.adobe.epubcheck.api.EPUBLocation;
import com.adobe.epubcheck.api.EpubCheck;
import com.adobe.epubcheck.api.Report;
import com.adobe.epubcheck.messages.Message;
import com.adobe.epubcheck.messages.MessageDictionary;
import com.adobe.epubcheck.messages.MessageId;
import com.adobe.epubcheck.util.FeatureEnum;

import java.io.File;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/30/2015
 */
public class Check implements Report {
    public static Boolean check() {
        File epubFile = new File("D:\\play\\epub\\public\\epub\\epub3.0\\q.epub");

        // simple constructor; errors are printed on stderr stream
        EpubCheck epubcheck = new EpubCheck(epubFile);

        // validate() returns true if no errors or warnings are found
        Boolean result = epubcheck.validate();
        return result;
    }

    @Override
    public void message(MessageId messageId, EPUBLocation epubLocation, Object... objects) {

    }

    @Override
    public void message(Message message, EPUBLocation epubLocation, Object... objects) {

    }

    @Override
    public void info(String s, FeatureEnum featureEnum, String s2) {

    }

    @Override
    public int getErrorCount() {
        return 0;
    }

    @Override
    public int getWarningCount() {
        return 0;
    }

    @Override
    public int getFatalErrorCount() {
        return 0;
    }

    @Override
    public int generate() {
        return 0;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setEpubFileName(String s) {

    }

    @Override
    public String getEpubFileName() {
        return null;
    }

    @Override
    public void setCustomMessageFile(String s) {

    }

    @Override
    public String getCustomMessageFile() {
        return null;
    }

    @Override
    public int getReportingLevel() {
        return 0;
    }

    @Override
    public void setReportingLevel(int i) {

    }

    @Override
    public void close() {

    }

    @Override
    public void setOverrideFile(File file) {

    }

    @Override
    public MessageDictionary getDictionary() {
        return null;
    }
}
