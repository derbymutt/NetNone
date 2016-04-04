/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javawebbrowser.JavaWebBrowser;
import javax.swing.*;
import userInterface.button.CancelButton;


/**
 *
 * @author derby_000
 */
public class Console extends JFrame{
    private static String display = "";
    private static String lastInput = "";
    private static JTextArea textDisplay;
    private static JScrollPane textPane;
    private JPanel textPanel;
    private static JPanel givePanel;
    
    public static boolean canUseConsole = true;
    
    
    private static final String verMajor = "1";
    private static final String verMinor = "2";
    private static final String verBuild = Definitions.BUILDNUMBER;
    private static String consoleVersion = verMajor+"."+verMinor+"."+verBuild;
    
    public Console()
    {
        startUI();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void startUI()
    {
        this.setLayout(new BorderLayout());
        this.setSize(800,400);
        
        textDisplay = new JTextArea();
        textDisplay.setBackground(Color.WHITE);
        textPane = new JScrollPane(textDisplay);
        textPanel = new JPanel();
        textPanel.setBorder(BorderFactory.createTitledBorder("Console"));
        
        //textDisplay.setPreferredSize(new Dimension(800,400));
        textPane.setSize(800,400);
        textPanel.setLayout(new GridLayout(1,1));
        textPanel.add(textPane);
        this.add(textPanel, BorderLayout.CENTER);
        
        JPanel bar = new JPanel();
        bar.add(new CancelButton("Force Exit"));
        this.add(bar, BorderLayout.SOUTH);
        
        givePanel = (JPanel)this.getContentPane();
        Console.writeRule(100);
        Console.writeln("\tCompiled with Java "+Constants.JDKVERSION);
        Console.writeln("\tCore version "+consoleVersion);
        Console.writeRule(100);
    }
    
    public void hideInstance()
    {
        try{
            this.setVisible(false);
        }catch(Exception ex)
        {
            
        }
    }
    public void showInstance()
    {
        try{
            this.setVisible(true);
        }catch(Exception ex)
        {
            
        }
    }
    
    public static JPanel getConsole()
    {
        try{
            return givePanel;
        }catch(Exception ex)
        {
            return null;
        }
    }
    
    public static String getVersion()
    {
        return consoleVersion;
    }
    
    
    public static void write(String text)
    {
        try{
            display = display+text;
            lastInput = text;

            textDisplay.setText(display);
        }
        catch(Exception ex)
        {
            
        }
    }
    
    public static void writeln(String text)
    {
        try{
            display = display+" "+text+"\n";
            lastInput = text+"\n";
            textDisplay.setText(display);
        }
        catch(Exception ex)
        {
            
        }
    }
    
    public static void writeln()
    {
        try{
            display = display+"\n";
            lastInput = "\n";
            textDisplay.setText(display);
        }
        catch(Exception ex)
        {
            
        }
    }
    
    public static String getOutput()
    {
        return display;
    }
    
    public static String getLastOutput()
    {
        return lastInput;
    }
    
    public static void clear()
    {
        display = "";
        lastInput = "";
        try{
            textDisplay.setText(display);
        }catch(Exception ex)
        {
            
        }
    }
    
    public static void writeRule(int length)
    {
        try{
            for(int i = 0; i<length; i++)
            {
                display = display+"-";

            }
            display = display+"\n";
            textDisplay.setText(display);
        }catch(Exception ex)
        {
            
        }
    }
    private class WindowListener implements WindowStateListener
    {
        @Override
        public void windowStateChanged(WindowEvent e)
        {
            if(JavaWebBrowser.console != null)
                JavaWebBrowser.console.repaint();
        }
    }
    
}
