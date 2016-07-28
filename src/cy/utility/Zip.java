package cy.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import cy.utility.Sys;
import cy.utility.debug.Log;

/**
 * Created by cychen on 7/11/16.
 * Code modified from http://www.mkyong.com/java/how-to-compress-files-in-zip-format/
 */
public class Zip {

    static List<String> fileList = new ArrayList<String>();
    static String sourceFolderRootPath;
    //static String sourceFolderPath;

    /**
     * Zips the selected folder (including the folder).
     * @param inFolderPath  The folder path to be zipped.
     * @param outFileName   The file name (including full path) of the generated zip file.
     * @return              Whether the output zip file is existed after zipping.
     */
    public static Boolean zipFolder(String inFolderPath, String outFileName) {
        fileList.clear();
        File sourceFolder = new File(inFolderPath);
        //sourceFolderPath = sourceFolder.getAbsolutePath();
        sourceFolderRootPath = sourceFolder.getParent();
        generateFileList(sourceFolder);
        zipIt(outFileName);
        return Sys.isFileExisted(outFileName);
    }

    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    private static void zipIt(String zipFile){

        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            //System.out.println("Output to Zip : " + zipFile);

            int fileCount = 0;
            for(String file : fileList){

                //System.out.println("File Added : " + file);
                ZipEntry ze= new ZipEntry(file);
                zos.putNextEntry(ze);

                FileInputStream in =
                        new FileInputStream(sourceFolderRootPath + File.separator + file);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
                fileCount += 1;
            }

            zos.closeEntry();
            //remember close it
            zos.close();

            //System.out.println("Done " + String.valueOf(fileCount));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     */
    private static void generateFileList(File node){

        //add file only
        if(node.isFile()){
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
        }

        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                generateFileList(new File(node, filename));
            }
        }

    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private static String generateZipEntry(String file) {
        //return file.substring(sourceFolderPath.length() + 1, file.length());
        return file.substring(sourceFolderRootPath.length() + 1, file.length());
    }


    /* ==================== The following is for unzip functions. ==================== */
    /**
     * Unzip it
     * @param zipFile input zip file
     * @param outputFolder zip file output folder
     */
    public static void unZipFile(File zipFile, String outputFolder){

        byte[] buffer = new byte[1024];

        try{

            //create output directory is not exists
            File folder = new File(outputFolder);
            if(!folder.exists()){
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){
                if (ze.isDirectory()) {
                    String fileName = ze.getName();
                    File newFile = new File(outputFolder + File.separator + fileName);
                    newFile.mkdirs();

                    ze = zis.getNextEntry();
                    continue;
                }
                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                System.out.println("file unzip : "+ newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

}
