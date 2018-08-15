package cy.utility.sys.serial;

import cy.utility.Sys;
import cy.utility.debug.Log;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by cychen on 7/22/16.
 */
public class SerialLister {
    public static native String[] listSerialsNative();
    private static Boolean isLibLoaded = false;

    static SerialLister instance = new SerialLister();  // This is to trigger the constructor once.
    private SerialLister() {
        // Load external "listSerialsj" lib. The loaded function is cy.utility.sys.serial.SerialLister.listSerialsNative.
        // The library source code is at https://github.com/arduino/listSerialPortsC
        loadLib();
    }

    public static List<String> listSerials() throws UnsatisfiedLinkError {
        if (isLibLoaded == false) {
            throw new UnsatisfiedLinkError("listSerialsj library is not loaded.");
        }
        return new ArrayList(Arrays.asList(listSerialsNative()));
    }

    public static List<String> listSerialsNames(){
        List<String> list = new LinkedList<>();
        for (String port : listSerialsNative()) {
            list.add(port.split("_")[0]);
        }
        return list;
    }

    private static void loadLib() {
        String libPath = getClassPath() + File.separator;

        if (Sys.isLinux()) {
            if (Sys.getCPUArch().contains("arm")) {
                libPath += "arm";
            } else if (Sys.getCPUArch().contains("64")) {
                libPath += "linux64";
            } else {
                libPath += "linux32";
            }
//        } else if (Sys.isMacOS()) {
//            libPath += "macosx";
//        } else if (Sys.isWindows()) {
//            libPath += "windows";
        } else {
            Log.errPutline("listSerialsj library does not support this OS or CPU architecture.");
            isLibLoaded = false;
            return;
        }

        File lib = new File (libPath, System.mapLibraryName("listSerialsj"));
        try {
            System.load(lib.getAbsolutePath());
            //System.loadLibrary("listSerialsj");
            Log.sysPutLine("lib loaded.");
            isLibLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Cannot load native library " + lib.getAbsolutePath());
            System.out.println("The program has terminated!");
            System.exit(1);
        }
    }

    public static String getCleanPath() {
        ClassLoader classLoader = SerialLister.class.getClassLoader();
        File classpathRoot = new File(classLoader.getResource("").getPath());

        return classpathRoot.getPath();
    }

    public static String getClassPath() {
        String classPath = getCleanPath()
                + File.separator + "cy"
                + File.separator + "utility"
                + File.separator + "sys"
                + File.separator + "serial";
        return classPath;
    }
}
