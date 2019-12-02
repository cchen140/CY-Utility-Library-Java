package cy.utility;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by cychen on 7/11/16.
 */
public class Sys {

    /**
     * returns true if running on windows.
     * https://github.com/arduino/Arduino/blob/8385aedc642d6cd76b50ac5167307121007e5045/arduino-core/src/processing/app/helpers/OSUtils.java
     */
    static public boolean isWindows() {
        //return PApplet.platform == PConstants.WINDOWS;
        return System.getProperty("os.name").contains("Windows");
    }

    /**
     * true if running on linux.
     * https://github.com/arduino/Arduino/blob/8385aedc642d6cd76b50ac5167307121007e5045/arduino-core/src/processing/app/helpers/OSUtils.java
     */
    static public boolean isLinux() {
        //return PApplet.platform == PConstants.LINUX;
        return System.getProperty("os.name").contains("Linux");
    }

    /**
     * returns true if Processing is running on a Mac OS X machine.
     * https://github.com/arduino/Arduino/blob/8385aedc642d6cd76b50ac5167307121007e5045/arduino-core/src/processing/app/helpers/OSUtils.java
     */
    static public boolean isMacOS() {
        //return PApplet.platform == PConstants.MACOSX;
        return System.getProperty("os.name").contains("Mac");
    }

    static public String getOSName() {
        return System.getProperty("os.name");
    }

    static public String getCPUArch() {
        return System.getProperty("os.arch");
    }


    /**
     * Get current program's root path.
     * @return The folder folder path without the last "\".
     */
    public static String getProgramRootPath() {
        return System.getProperty("user.dir");
    }

    public static ArrayList<File> getFolderTree(String inRootFolderPath) {
        File file = new File(inRootFolderPath);
        ArrayList<File> folderTree = getSubFoldersRecursive(file);
        //for (File thisFolder : folderTree) {
        //    Log.sysPutLine(thisFolder.getPath());
        //}
        return folderTree;
    }

    /**
     * This function lists all the sub folders recursively.
     * @param inFolder
     * @return A list of File objects pointing to corresponding folders.
     */
    private static ArrayList<File> getSubFoldersRecursive(File inFolder) {
        ArrayList<File> outFolders = new ArrayList<>();
        outFolders.add(inFolder);

        File[] folders = inFolder.listFiles(File::isDirectory);
        for (File thisFolder : folders) {
            outFolders.addAll( getSubFoldersRecursive(thisFolder) );
        }
        return outFolders;
    }

    public static String getFolderName(String inRootFolderPath) {
        File folder = new File(inRootFolderPath);
        return folder.getName();
    }

    public static String getFirstSubFolderName(String inRootFolderPath) {
        File folder = new File(inRootFolderPath);
        File[] subFolders = folder.listFiles(File::isDirectory);
        if (subFolders.length > 0) {
            return subFolders[0].getName();
        } else {
            return "";
        }
    }

    public static Boolean isFileExisted(String inFilePath) {
        File file = new File(inFilePath);
        return file.exists() && !file.isDirectory();
    }

    public static Boolean isFolderExisted(String inFolderPath) {
        File file = new File(inFolderPath);
        return file.exists() && file.isDirectory();
    }

    public static void createFolder(String inFolderPath) {
        File folder = new File(inFolderPath);
        if(!folder.exists()){
            folder.mkdir();
        }
    }

    public static ArrayList<File> getFilesByExtensionInFolder(String inFolderPath, String extension) {
        ArrayList<File> files = new ArrayList<>();
        if (!isFolderExisted(inFolderPath))
            return files;

        File folder = new File(inFolderPath);
        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(extension)) {
                files.add(file);
            }
        }
        return files;
    }

    public static String currentTimeString() {
        Double time = new Double(System.currentTimeMillis() / 1000.0);

        DecimalFormat formatter = new DecimalFormat("#.##");
        return formatter.format(time);
    }

//    public static byte[] charToBytes(char[] chars) {
//        CharBuffer charBuffer = CharBuffer.wrap(chars);
//        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
//        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
//                byteBuffer.position(), byteBuffer.limit());
//        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
//        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
//        return bytes;
//    }

    public static byte[] charToBytes(char[] inChars) {
        byte[] b = new byte[inChars.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) inChars[i];
        }
        return b;
    }

}
