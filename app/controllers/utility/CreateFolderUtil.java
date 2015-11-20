package controllers.utility;

import java.io.File;

/**
 * Copyright 2015 Erealm Info & Tech.
 * <p>
 * Created by alex on 11/20/2015
 */
public class CreateFolderUtil {
    public static boolean createFolder(String folderPath) {
        File folderFile = new File(folderPath);
        if (folderFile.exists()) {
            return true;
        }
        //Create Folder
        if (folderFile.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }
}
