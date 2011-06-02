package utils;


import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for logging information to either console or file
 * @author LAQ
 */
public class LogPrinter {
    static {
      openFile();
    }

    private static FileWriter fstream;
    private static int debugLevel = 3;


    /**
     * print to console the string s if the debug level is bigger than level
     * @param s
     * @param level
     */
    public static void printConsole(String s, int level) {
        if (level < debugLevel) {
            System.out.println(s);
        }
    }
    private static void openFile(){
        try {
            // Create file
            fstream = new FileWriter("out.txt");
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void writeFile(String s) {
        try {
            // Create file
            
            fstream.write(s);
            fstream.flush();
            
            //BufferedWriter out = new BufferedWriter(fstream);
            //out.w(s);
            //Close the output stream
            //out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error on Write: " + e.getMessage());
            e.printStackTrace();
        }

    }
    
    public static void closeFile(){
        try {
            fstream.close();
        } catch (IOException ex) {
            Logger.getLogger(LogPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Print data to file, being values a set of strings
     * @param values
     */
    public static void printData(String... values ){
        for(String s:values){
            writeFile(s+"\t");
        }
        writeFile("\n");
    }


}
