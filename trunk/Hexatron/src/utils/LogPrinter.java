/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author LAQ
 */
public class LogPrinter {
    private static int debugLevel=4;

    public static void printConsole(String s, int level){
        if(level<debugLevel){
            System.out.println(s);
        }
    }


}
