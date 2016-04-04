/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;
import javawebbrowser.JavaWebBrowser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import userInterface.UI;

/**
 *
 * @author derby_000
 */
public class WriteBookmarks {
    
    public static boolean Initialize()
    {
        try{
            Definitions.userPath = System.getProperty("user.home");

            Definitions.userDocuments = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();

            Console.writeln("User's home path is "+Definitions.userPath);
            return true;
        }
        catch(Exception ex)
        {
            Console.writeln(">>>MAJOR EXCEPTION\n\tFailed to aquire system info, bookmarks, file downloading, and settings may not be usable!");
            return false;
        }
    }
    
    public static void WriteBookmarks()
    {
        
        try{
            File targetFile = new File(Definitions.userDocuments+"\\NetNone\\bookmarks\\bookmarks.txt");
            File parent = targetFile.getParentFile();
            if(!parent.exists() && !parent.mkdirs()){
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }
            String input = "";
            Scanner scan;
            Writer write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8"));
            try{
                scan = new Scanner(targetFile);
            }catch (Exception ex)
            {
                write.write("");
                scan = new Scanner(targetFile);
            }
            while(scan.hasNextLine())
                input = input+scan.nextLine()+"\n";
           
            String output = "";
            for(int i = 0; i<javawebbrowser.JavaWebBrowser.ui.bookmarksLinks.size(); i++)
            {
                if(i != javawebbrowser.JavaWebBrowser.ui.bookmarksLinks.size()-1)
                    output = output + javawebbrowser.JavaWebBrowser.ui.bookmarksLinks.get(i).getText()+"\n";
                else
                    output = output + javawebbrowser.JavaWebBrowser.ui.bookmarksLinks.get(i).getText();
            }
            Console.writeln(output);
            //write.write(output);
            write.write(input+javawebbrowser.JavaWebBrowser.ui.bookmarksLinks.get(javawebbrowser.JavaWebBrowser.ui.bookmarksLinks.size()-1).getText()+"\n");
            write.close();
            Console.writeln("finished");
        }catch(Exception ex){}
    }
    
    public static void WriteSettings()
    {
        
        try{
            File targetFile = new File(Definitions.userDocuments+"\\NetNone\\settings\\settings.txt");
            File parent = targetFile.getParentFile();
            if(!parent.exists() && !parent.mkdirs()){
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }
            String input = "";
            Writer write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8"));
            String output = "";
            if(Constants.DIALUP)
                output = "true \n";
            else
                output = "false \n";
            
            if(Constants.SLOWMODE)
                output = output+"true";
            else
                output = output+"false";
            
            Console.writeln(output);
            //write.write(output);
            write.write(output);
            write.close();
            Console.writeln("finished");
        }catch(Exception ex){Console.writeln("Failed\n"+ex.getMessage());}
    }
    
    public static void WriteJar(String filename, String jar)
    {
        try{
            File targetFile = new File(Definitions.userDocuments+"\\NetNone\\Downloads\\"+filename.replace("/", "\\"));
            File parent = targetFile.getParentFile();
            if(!parent.exists() && !parent.mkdirs()){
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }
            //Writer write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)
            URL website = new URL("http://www.website.com/information.asp");
            ReadableByteChannel rbc = Channels.newChannel(new URL(javawebbrowser.JavaWebBrowser.core.url).openStream());
            FileOutputStream fos = new FileOutputStream(targetFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            //Console.writeln(output);
            //write.write(jar);
            //write.close();
            Console.writeln("finished writing "+filename);
        }catch(Exception ex){}
    }
    
    public static void ReadBookmarks(UI ui)
    {
        Console.writeln("Attempting to read bookmark file");
        String temp = "";
        try{
            Scanner scan = new Scanner(new File(Definitions.userDocuments+"\\NetNone\\bookmarks\\bookmarks.txt"));
            //Definitions.currentInputFile = "";
            while(scan.hasNextLine())
            {
                temp = scan.nextLine();
                if(temp.startsWith("folder") && !temp.endsWith("end"))
                {
                    JMenu tempm = new JMenu(temp.replace("folder ", ""));
                    ui.addBookmark(tempm);
                    if(scan.hasNextLine())
                        temp = scan.nextLine();
                    Console.writeln(temp);
                    while(!temp.startsWith("folder") && scan.hasNextLine())
                    {
                        
                        Console.writeln(temp+" a");
                        JMenuItem tempb = ui.createBookmark(temp);
                        tempm.add(tempb);
                        temp = scan.nextLine();
                        
                    }
                    
                }
                else if(ui != null)
                    ui.addBookmark(temp);
                else
                    Console.writeln("Got Null reference!");
            }
            //JavaWebBrowser.ui.applyBookmarks();
            //Console.writeRule(125);
            //Console.write(Definitions.currentInputFile);
            //Console.writeRule(125);
            Console.writeln("Parsed config file successfully.");
            Console.writeln("Attempting to update UI...");
            
            Console.writeln("Updated UI successfully.");
        }
        catch(Exception ex)
        {
            //JOptionPane.showMessageDialog(null, "Cannot find file \""+Definitions.userDocuments+"\\NetNone\\bookmarks\\bookmarks.txt.\nCreating it now");
            Console.writeln("Caught Exception\n"+ex.getMessage()+"\t"+Definitions.userDocuments+"\\NetNone\\bookmarks\\bookmarks.txt\n"+temp);
                    
        }
        
    }
    public static void ReadConfig()
    {
        Console.writeln("Attempting to read config file");
        String temp = "";
        try{
            Scanner scan = new Scanner(new File(Definitions.userDocuments+"\\NetNone\\settings\\settings.txt"));
            //Definitions.currentInputFile = "";
            temp = scan.next();
            Console.writeln(temp);
            if(temp.equals("true"))
                Constants.DIALUP = true;
            else
                Constants.DIALUP = false;
            temp = scan.next();
            Console.writeln(temp);
            if(temp.equals("true"))
                Constants.SLOWMODE = true;
            else
                Constants.SLOWMODE = false;
            
            //JavaWebBrowser.ui.applyBookmarks();
            //Console.writeRule(125);
            //Console.write(Definitions.currentInputFile);
            //Console.writeRule(125);
            Console.writeln("Parsed config file successfully.");
            Console.writeln("Attempting to update UI...");
            
            Console.writeln("Updated UI successfully.");
        }
        catch(Exception ex)
        {
            //JOptionPane.showMessageDialog(null, "Cannot find file \""+Definitions.userDocuments+"\\NetNone\\bookmarks\\bookmarks.txt.\nCreating it now");
            Console.writeln("Caught Exception\n"+ex.getMessage()+"\t"+Definitions.userDocuments+"\\NetNone\\settings\\settings.txt\n"+temp);
                    
        }
        
    }
}


