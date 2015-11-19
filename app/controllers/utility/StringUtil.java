package controllers.utility;


public class StringUtil {

    /**
     * Returns <code>true</code> if the provided String is either <code>null</code>
     * or the empty String <code>""</code>.<p>
     *
     * @param value the value to check
     * @return true, if the provided value is null or the empty String, false otherwise
     */
    public static boolean isEmpty(String value) {
        return (value == null) || (value.length() == 0);
    }

    /**
     * extract file name form the given file path
     *
     * @param fileFullPath      path to the file, like 'c:/test.jpg', 'c:\\test.jpg'
     * @param withExtention indicate contain file.extention. true : contain | false : ignore
     * @return fileName file.name;
     */
    public static String getFileName(String fileFullPath, boolean withExtention) {
        int sep = fileFullPath.lastIndexOf("\\") == -1 ? fileFullPath.lastIndexOf("/") : fileFullPath.lastIndexOf("\\");
        if (withExtention)
            return fileFullPath.substring(sep + 1);
        return fileFullPath.substring(sep + 1, fileFullPath.lastIndexOf("."));
    }

    /**
     * get path to the given file , e.g. : c:\test\aaa.html -> c:\test\
     *
     * @param fileFullPath path to file;
     * @return
     */
    public static String getFilePath(String fileFullPath) {
        int sep = fileFullPath.lastIndexOf("\\") == -1 ? fileFullPath.lastIndexOf("/") : fileFullPath.lastIndexOf("\\");
        return fileFullPath.substring(0, sep + 1);
    }
}
