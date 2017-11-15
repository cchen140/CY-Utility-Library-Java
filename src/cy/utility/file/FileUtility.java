package cy.utility.file;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/*
<!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.5</version>
</dependency>
*/
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 * Created by cychen on 7/26/16.
 * http://stackoverflow.com/questions/1146153/copying-files-from-one-directory-to-another-in-java
 */
public class FileUtility {
    public static void copyDirectory(File sourceLocation , File targetLocation)
            throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    /**
     * This outputs the folder names in the first layer.
     * @param inFolderPath
     * @return folder names in the first layer
     */
    public static ArrayList<String> getSubFolderNames(String inFolderPath) {
        File folder = new File(inFolderPath);
        File[] subFolders = folder.listFiles(File::isDirectory);
        ArrayList outFolderNames = new ArrayList<String>();
        for (File thisFolder : subFolders) {
            outFolderNames.add(thisFolder.getName());
        }
        return outFolderNames;
    }


    /* Get the newest file for a specific extension
     * https://stackoverflow.com/questions/285955/java-get-the-newest-file-in-a-directory
     */
    public static File getTheNewestFile(String filePath, String ext) {
        File theNewestFile = null;
        File dir = new File(filePath);
        FileFilter fileFilter = new WildcardFileFilter("*." + ext);
        File[] files = dir.listFiles();// (fileFilter);

        if (files.length > 0) {
            /** The newest file comes first **/
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            theNewestFile = files[0];
        }

        return theNewestFile;
    }
}
