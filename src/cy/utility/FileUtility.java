package cy.utility;

import java.io.*;
import java.util.ArrayList;

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
}
