package cy.utility.debug;

import java.text.DecimalFormat;
import java.util.Formatter; // for StyledDocument
import java.awt.*;  // for Color
import javax.swing.text.*;

/**
 * Created by cychen on 7/8/16.
 */
public class Log {
    private static Double initialTime = new Double(System.currentTimeMillis() / 1000.0);
    private static StyledDocument outDoc = null;
    private static Boolean shouldOutDocPrintPrefix = false;
    private static Boolean shouldOutDocPrintSysLog = false;

    public static void setDocument(StyledDocument inDoc, Boolean inShouldOutDocPrintPrefix, Boolean inShouldOutDocPrintSysLog) {
        shouldOutDocPrintPrefix = inShouldOutDocPrintPrefix;
        shouldOutDocPrintSysLog = inShouldOutDocPrintSysLog;
        outDoc = inDoc;
        sysPutLine("Log messenger initialized.");
    }

    private static String getPrefix() {
        Throwable t = new Throwable();
        StackTraceElement currentSte = t.getStackTrace()[2];
        //String methodName = currentSte.getMethodName();
        String classFullName = currentSte.getClassName();
        String classNameSplit[] = classFullName.split("\\.");
        String className = classNameSplit[classNameSplit.length-1];
        //String classMethodName = className + "." + methodName;
        //classMethodName = classMethodName.length()>30 ? "~" + classMethodName.substring(classMethodName.length()-29) : classMethodName;
        className = className.length()>20 ? "~" + className.substring(className.length()-19) : className;
        return (new Formatter().format("[%10s][%-20s]\t", currentTimeString(), className)).toString();
    }

    public static void cmdOnlyPutline(String format, Object... args) {
        System.err.format(getPrefix() + format + "\r\n", args);
    }

    /**
     * This function prints the string to outDoc regardless of the "shouldOutDocPrintSysLog" setting.
     * @param format
     * @param args
     */
    public static void putLineOutDoc(String format, Object... args) {
        String formatWithPrefix = getPrefix() + format;
        String strToOutDoc;

        if (shouldOutDocPrintPrefix == true) {
            strToOutDoc = formatWithPrefix;
        } else {
            strToOutDoc = format;
        }

        colorPutLineToOutDoc(new Formatter().format(strToOutDoc, args).toString(), Color.black);
        System.out.format(formatWithPrefix + "\r\n", args);
    }

    public static void errPutline(String format, Object... args) {
        String formatWithPrefix = getPrefix() + format;

        if (shouldOutDocPrintSysLog == true) {
            String strToOutDoc;

            if (shouldOutDocPrintPrefix == true) {
                strToOutDoc = formatWithPrefix;
            } else {
                strToOutDoc = format;
            }

            colorPutLineToOutDoc(new Formatter().format(strToOutDoc, args).toString(), Color.red);
        }

        System.err.format(formatWithPrefix + "\r\n", args);
    }

    public static void sysPutLine(String format, Object... args) {
        String formatWithPrefix = getPrefix() + format;

        if (shouldOutDocPrintSysLog == true) {
            String strToOutDoc;

            if (shouldOutDocPrintPrefix == true) {
                strToOutDoc = formatWithPrefix;
            } else {
                strToOutDoc = format;
            }

            colorPutLineToOutDoc(new Formatter().format(strToOutDoc, args).toString(), Color.blue);
        }

        System.out.format(formatWithPrefix + "\r\n", args);
    }

    private static void colorPutLineToOutDoc(String inStr, Color inColor) {
        if (outDoc == null) {
            //System.err.println("Error: outDoc in ProgramLogMessenger has not been initialized.");
            // Just do nothing is fine.
            return;
        }

        try {
            outDoc.insertString(outDoc.getLength(), inStr + "\r\n", null);

            Style style = outDoc.addStyle("newStyle", null);
//            StyleConstants.setFontFamily(errStyle, "monospaced");
            StyleConstants.setForeground(style, inColor);

//            outDoc.setParagraphAttributes(0, 1, errStyle, false);
            outDoc.setCharacterAttributes(outDoc.getLength() - inStr.length() - 2, inStr.length(), style, false);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private static String currentTimeString() {
        Double time = new Double(System.currentTimeMillis() / 1000.0);
        return (new Formatter().format("%.2f", time-initialTime)).toString();
    }
}
