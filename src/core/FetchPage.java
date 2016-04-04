/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.net.URL;
import javax.swing.JEditorPane;

/**
 *
 * @author derby_000
 */
public class FetchPage {
    
    public static JEditorPane get(String url)
    {
        JEditorPane editorPane = new JEditorPane();
        URL rorServerList;
         try{
                rorServerList = new URL(url);
                if (rorServerList != null) {
                    try {
                        editorPane.setPage(rorServerList);
                        editorPane.doLayout();
                        Console.writeln("Got URL "+rorServerList);
                        
                    } catch (IOException e) {
                        Console.writeln("Attempted to read a bad URL: " + rorServerList+"\n\t"+e.getMessage()+"\n"+e.getStackTrace());
                    }
                 } else {
                     Console.writeln("Couldn't find file: "+url);
                 }
                
             }
            catch(Exception ex){Console.writeln("Failed to create webservice\n\t"+ex.getMessage()+"\n"+ex.getStackTrace());}
         return editorPane;
    }
}
