/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import core.Console;
import core.Constants;
import core.WriteBookmarks;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import javawebbrowser.JavaWebBrowser;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author derby_000
 */
public class UI extends JFrame {
    
    public ArrayList<JScrollPane> mainScrollPane;
    public JPanel main;
    //public JEditorPane editorPane;
    public JMenuBar menu;
    
    public JMenu file;
    public JMenuItem fileRefresh;
    public JMenuItem fileExit;
    public JMenuItem fileSave;
    
    public JMenu view;
    public JMenuItem viewConsole;
    public JMenuItem hideConsole;
    
    public JTextArea menuTextEntry;
    public JButton menuGo;
    public JLabel menuLoad;
    public JButton menuHome;
    
    public JMenu history;
    public ArrayList<JMenuItem> historyLinks;
    public ArrayList<JComponent> editorPane;
    public ArrayList<String> serverList;
    public boolean extension = false;
    public int extensionLink = 0;
    
    public JMenu bookmarks;
    public JButton bookmarksMake;
    public ArrayList<JMenuItem> bookmarksLinks;
    private BufferedImage image;
    private BufferedImage image2;
    
    private JButton back;
    private JButton forward;
    private BufferedImage[] navButtons;
    private int index = 0;
    
    private JPanel loadingPanel;
    private JTextArea loadingBar;
    
    private JCheckBox dialup;
    private JCheckBox slowmode;
    private JMenuItem save;
    private JMenuItem console;
    
    private JButton download;
    
    public final int BARSIZE = 100;
    
    public UI()
    {
        try
        {
            WriteBookmarks.Initialize();
            WriteBookmarks.ReadConfig();
            
            navButtons = new BufferedImage[6];
            serverList = new ArrayList();
            bookmarksLinks = new ArrayList();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.addWindowStateListener(new WindowListener());
            image = ImageIO.read(getClass().getResourceAsStream("NetNoneLogo2.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("NetNoneLogo3.png"));
            
            navButtons[0] = ImageIO.read(getClass().getResourceAsStream("back.png"));
            navButtons[1] = ImageIO.read(getClass().getResourceAsStream("forward.png"));
            navButtons[2] = ImageIO.read(getClass().getResourceAsStream("back_down.png"));
            navButtons[3] = ImageIO.read(getClass().getResourceAsStream("forward_down.png"));
            navButtons[4] = ImageIO.read(getClass().getResourceAsStream("back_hover.png"));
            navButtons[5] = ImageIO.read(getClass().getResourceAsStream("forward_hover.png"));
            
            this.setIconImage(image);
            menu = new JMenuBar();
            editorPane = new ArrayList();
            editorPane.add(JavaWebBrowser.getCore().getEditorPane());
            ((JEditorPane)editorPane.get(editorPane.size()-1)).setEditable(false);
            ((JEditorPane)editorPane.get(editorPane.size()-1)).addHyperlinkListener(new LinkListener());
            editorPane.set(editorPane.size()-1,JavaWebBrowser.getCore().getEditorPane());
            Clip clip;
            try{
                if(!Constants.DIALUP)
                    throw new Exception("Dialup Disabled!");
                ((JEditorPane)editorPane.get(editorPane.size()-1)).setPage(getClass().getResource("loading.html"));
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("dialup.wav"));
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
                
                
            }catch(Exception ex){Console.writeln("Failed "+ex.getMessage());}
            Console.writeln("Adding to pane");
            mainScrollPane = new ArrayList();
            mainScrollPane.add(new JScrollPane(editorPane.get(editorPane.size()-1)));
            main = new JPanel();
            main.setLayout(new GridLayout(1,1));
            main.add(mainScrollPane.get(mainScrollPane.size()-1),BorderLayout.CENTER);
            this.setLayout(new BorderLayout());
            createMenu();
            this.setJMenuBar(menu);
            this.add(main,BorderLayout.CENTER);
            this.setSize(640,540);
            ApplyConfig();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setUndecorated(false);
            //mainPane2.setResizable(false);
            this.setTitle("Net None");
            this.setVisible(true);
            repaintWipe2(250);

           
        } 
        catch (Exception ex)
        {
            Console.writeln("Failed to create pane\n\t"+ex.getMessage());
        }
    }
    
    public void refresh()
    {
        
        try{
            repaintWipe(100);
            URL temp = new URL(JavaWebBrowser.core.url);
            String a = temp.toString();
            if(a.endsWith(".jpg") || a.endsWith(".png") || a.endsWith(".bmp") || a.endsWith(".gif"))
            {
                refreshImage(temp);
                return;
            }
            if(a.endsWith(".jar"))
            {
                Console.writeln("Got jar");
                JEditorPane temp1 = JavaWebBrowser.getCore().getEditorPane();
                Console.writeln(""+System.currentTimeMillis());
                Thread.sleep((long)10);
                Console.writeln(""+System.currentTimeMillis());
                String tempJar = temp1.getText();
                WriteBookmarks.WriteJar(temp.getFile().replace("/NetNone/Build/", ""),tempJar);
                //Console.writeln("Writing file "+temp.getFile()+"\n"+tempJar);
                return;
            }
            menuLoad.setText("Loading 15%");
            editorPane.add(new JEditorPane());
            menuLoad.setText("Loading 20%");
            ((JEditorPane)editorPane.get(editorPane.size()-1)).setPage(menuTextEntry.getText());
            
            ((JEditorPane)editorPane.get(editorPane.size()-1)).setEditable(true);
            ((JEditorPane)editorPane.get(editorPane.size()-1)).addHyperlinkListener(new LinkListener());
            ((JEditorPane)editorPane.get(editorPane.size()-1)).setEditable(false);
            JScrollPane pane = new JScrollPane();
            
            mainScrollPane.add(new JScrollPane(editorPane.get(editorPane.size()-1)));
            //this.setVisible(false);
            main.remove(mainScrollPane.get(index));
            main.add(mainScrollPane.get(mainScrollPane.size()-1),BorderLayout.CENTER);
            index = mainScrollPane.size()-1;
            this.update(this.getGraphics());
            //this.setVisible(true);
            historyLinks.add(new JMenuItem(JavaWebBrowser.core.url));
            history.add(historyLinks.get(historyLinks.size()-1));
            historyLinks.get(historyLinks.size()-1).addActionListener(new HistoryListener());
            if(historyLinks.size()>26)
            {
                history.remove(historyLinks.get(0));
                historyLinks.remove(0);
                
            }
            
            Console.writeln("Showing URL "+((JEditorPane)editorPane.get(editorPane.size()-1)).getPage().toString());
        }catch(Exception ex){
            menuLoad.setText("Loading FAILED");
            menuTextEntry.setText("netnone://404.html");
            menuLoad.setText("Loading 10%");

            refreshResource();
            return;
        }
        this.setTitle("Net None - " +JavaWebBrowser.core.url);
       menuLoad.setText("Ready");
        
        
    }
    
    public void refreshResource()
    {
        String text = "";
        try{
            menuLoad.setText("Loading 15%");
            repaintWipe(100);
            
            text = menuTextEntry.getText().replace("netnone://","");
            if(text.equals("console"))
            {
                editorPane.add(Console.getConsole());
            }
            else
            {
                editorPane.add(JavaWebBrowser.getCore().getEditorPane());
                ((JEditorPane)editorPane.get(editorPane.size()-1)).setPage(getClass().getResource(text));
                menuLoad.setText("Loading 20%");
                URL temp = new URL(JavaWebBrowser.core.url);
                ((JEditorPane)editorPane.get(editorPane.size()-1)).setEditable(true);
                ((JEditorPane)editorPane.get(editorPane.size()-1)).addHyperlinkListener(new LinkListener());
                ((JEditorPane)editorPane.get(editorPane.size()-1)).setEditable(false);
            }
            
            JScrollPane pane = new JScrollPane();
            mainScrollPane.add(new JScrollPane(editorPane.get(editorPane.size()-1)));
            //this.setVisible(false);
            main.remove(mainScrollPane.get(index));
            main.add(mainScrollPane.get(mainScrollPane.size()-1),BorderLayout.CENTER);
            index = mainScrollPane.size()-1;
            this.update(this.getGraphics());
            //this.setVisible(true);
            
            
            Console.writeln("Showing URL "+((JEditorPane)editorPane.get(editorPane.size()-1)).getPage().toString());
        }catch(Exception ex){
            menuLoad.setText("Loading FAILED");
            Console.writeln("Failed to get "+text);
            menuTextEntry.setText("netnone://404.html");
            //menuLoad.setText("Loading 10%");

            //refreshResource();
            return;
        }
        this.setTitle("Net None - " +text.replace(".html",""));
       menuLoad.setText("Ready");
        
        
    }
    
     public void refreshImage(URL temp)
    {
        String text = "";
        try{
            menuLoad.setText("Loading 15%");
            ImageIcon img = new ImageIcon(temp);

            JLabel label = new JLabel();
            label.setIcon(img);
            Console.writeln("1");
            JPanel panel = new JPanel();
            Console.writeln("2");
            panel.add( label);
            Console.writeln("3");
            editorPane.add(panel);
            Console.writeln("4");
            menuLoad.setText("Loading 20%");
            
            mainScrollPane.add(new JScrollPane(editorPane.get(editorPane.size()-1)));
            Console.writeln("5");
            //this.setVisible(false);
            main.remove(mainScrollPane.get(index));
            
            main.add(mainScrollPane.get(mainScrollPane.size()-1),BorderLayout.CENTER);
            index = mainScrollPane.size()-1;
            //this.update(this.getGraphics());
            //this.setVisible(true);
            historyLinks.add(new JMenuItem(JavaWebBrowser.core.url));
            history.add(historyLinks.get(historyLinks.size()-1));
            historyLinks.get(historyLinks.size()-1).addActionListener(new HistoryListener());

            if(historyLinks.size()>26)
            {
                history.remove(historyLinks.get(0));
                historyLinks.remove(0);
                
            }
            
            //Console.writeln("Showing URL "+((JPanel)editorPane.get(editorPane.size()-1)).getPage().toString());
        }catch(Exception ex){
            menuLoad.setText("Loading FAILED");
            Console.writeln("Failed to get "+temp.toString()+"\n"+ex.getMessage());
            menuTextEntry.setText("netnone://404.html");
            //menuLoad.setText("Loading 10%");

            //refreshResource();
            return;
        }
        this.setTitle("Net None - " +text.replace(".html",""));
       menuLoad.setText("Ready");
        
        
    }
    
    private void createMenu()
    {
        menu = new JMenuBar();
        menu.setLayout(new GridLayout(2,1));
        JMenuBar menu1 = new JMenuBar();
        JMenuBar menu2 = new JMenuBar();
        file = new JMenu("File");
        view = new JMenu("View");
        history = new JMenu("History");
        
        historyLinks = new ArrayList();
        
        
        bookmarks = new JMenu("Bookmarks");
        bookmarksMake = new JButton("Add Bookmark");
        bookmarksMake.addActionListener(new bookmarksListener());
        
        
        
        
        WriteBookmarks.ReadBookmarks(this);
        
        menuTextEntry = new JTextArea("");
        menuTextEntry.setRows(1);
        menuTextEntry.setBorder(BorderFactory.createEtchedBorder());
        menuGo = new JButton("GO");
        menuGo.addActionListener(new GoListener());
        menuLoad = new JLabel("Ready");
        menuHome = new JButton("Home");
        menuHome.setIcon(new ImageIcon(image2));
        menuHome.addActionListener(new HomeListener());
        
        back = new JButton("",new ImageIcon(navButtons[0]));
        back.setPressedIcon(new ImageIcon(navButtons[2]));
        back.setRolloverIcon(new ImageIcon(navButtons[4]));
        back.addActionListener(new BackListener());
        back.setIconTextGap(0);
        
        forward = new JButton("",new ImageIcon(navButtons[1]));
        forward.setPressedIcon(new ImageIcon(navButtons[3]));
        forward.setRolloverIcon(new ImageIcon(navButtons[5]));
        forward.addActionListener(new ForwardListener());
        
        download = new JButton("Download");
        download.addActionListener(new DownloadListener());
        
        fileRefresh = new JMenuItem("Refresh",KeyEvent.VK_R);
        fileRefresh.setMnemonic(KeyEvent.VK_R);
        fileRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        fileRefresh.addActionListener(new RefreshListener());
        
        fileSave = new JMenuItem("Save",KeyEvent.VK_S);
        fileSave.setMnemonic(KeyEvent.VK_S);
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileSave.addActionListener(new DownloadListener());
        
        fileExit = new JMenuItem("Exit",KeyEvent.VK_W);
        fileExit.setMnemonic(KeyEvent.VK_W);
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        fileExit.addActionListener(new QuitListener());
        
        viewConsole = new JMenuItem("Show Console");
        viewConsole.addActionListener(new ConsoleListener());
        hideConsole = new JMenuItem("Hide Console");
        console = new JMenuItem("Load Console");
        console.addActionListener(new ConsoleListener());
        hideConsole.addActionListener(new ConsoleListener());
        
        dialup = new JCheckBox("Dialup Mode");
        slowmode = new JCheckBox("Slow Page Loading");
        save = new JMenuItem("Save settings");
        save.addActionListener(new ConfigListener());
        
        file.add(fileRefresh);
        file.add(fileSave);
        file.add(fileExit);
        
        view.add(viewConsole);
        view.add(hideConsole);
        view.add(console);
        view.add(dialup);
        view.add(slowmode);
        view.add(save);
        
        JLabel bar = new JLabel("_");
        bar.setMaximumSize(new Dimension(2,20));
        bar.setBorder(BorderFactory.createEtchedBorder());
        
        loadingPanel = new JPanel();
        loadingPanel.setMinimumSize(new Dimension(BARSIZE,20));
        loadingPanel.setMaximumSize(new Dimension(BARSIZE,20));
        loadingPanel.setPreferredSize(new Dimension(BARSIZE,20));
        loadingPanel.setBorder(BorderFactory.createEtchedBorder());
        loadingBar = new JTextArea();
        loadingBar.setEditable(false);
        loadingBar.setBackground(Color.BLUE);
        loadingBar.setMinimumSize(new Dimension(0,12));
        loadingBar.setMaximumSize(new Dimension(BARSIZE,12));
        loadingBar.setPreferredSize(new Dimension(BARSIZE,12));
        loadingPanel.add(loadingBar);
        JPanel panelz = new JPanel();
        panelz.setLayout(new BorderLayout());
        panelz.add(menuLoad,BorderLayout.WEST);
        panelz.add(loadingPanel,BorderLayout.EAST);

        menu1.add(file);
        menu1.add(view);
        menu1.add(history);
        menu1.add(bookmarks);
        menu1.add(new JLabel("  "));
        menu1.add(bar);
        menu1.add(new JLabel("  "));
        this.add(panelz,BorderLayout.SOUTH);
        menu1.add(bookmarksMake);
        menu1.add(menuHome);
        menu1.add(download);
        menu2.add(back);
        menu2.add(forward);
        menu2.add(menuGo);
        menu2.add(menuTextEntry);
        
        menu.add(menu1);
        menu.add(menu2);
    }
    private class GoListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(menuTextEntry.getText().startsWith("netnone://"))
            {
                menuTextEntry.setText(menuTextEntry.getText());
                menuLoad.setText("Loading 10%");

                refreshResource();
                return;
            }
            if(!menuTextEntry.getText().startsWith("http://") && !menuTextEntry.getText().startsWith("https://"))
                menuTextEntry.setText("http://"+menuTextEntry.getText());
            Console.writeln(">>> Got "+menuTextEntry.getText()+" from UI.");
            menuLoad.setText("Loading 0%");
            JavaWebBrowser.core.getPage(menuTextEntry.getText());
            menuLoad.setText("Loading 10%");
            extension = false;
            refresh();
                
        }
        
    }
    
    private class HomeListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            menuTextEntry.setText("netnone://splash.html");
            menuLoad.setText("Loading 10%");

            refreshResource();
            return;

           
        }
        
    }
    
    public void repaintWipe(int time)
    {
        if(!Constants.SLOWMODE)
            return;
        for(int i = 0; i<time; i++)
        {
            
            menuLoad.setText("Loading "+(i/time)+"%");
            menuLoad.setVisible(false);
            menuLoad.setVisible(true);
            int temp = (int)((double)BARSIZE*((i/(time*1.0))));
            loadingBar.setSize(new Dimension(temp,12));
            loadingBar.setPreferredSize(new Dimension(temp,12));
            loadingPanel.update(loadingPanel.getGraphics());
            while(loadingPanel.isPaintingTile())
            {}
            try{Thread.sleep((long)75);}catch(Exception ex){Console.writeln("Caught Exception");}
            //canvas.repaint();
        }
    }
    public void repaintWipe2(int time)
    {
            if(Constants.DIALUP)
            for(int i = 0; i<time; i++)
            {

                menuLoad.setText("Loading "+(i/time)+"%");
                menuLoad.setVisible(false);
                menuLoad.setVisible(true);
                int temp = (int)((double)BARSIZE*((i/(time*1.0))));
                loadingBar.setSize(new Dimension(temp,12));
                loadingBar.setPreferredSize(new Dimension(temp,12));
                loadingPanel.update(loadingPanel.getGraphics());
                while(loadingPanel.isPaintingTile())
                {}
                try{Thread.sleep((long)100);}catch(Exception ex){Console.writeln("Caught Exception");}
                //canvas.repaint();
            }
        menuTextEntry.setText("netnone://splash.html");
        refreshResource();
    }
    public class HistoryListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String temp = menuTextEntry.getText();
            for(int i = 0; i<historyLinks.size(); i++)
            {
                if(e.getSource() == historyLinks.get(i))
                    temp = historyLinks.get(i).getText();
            }
            if(temp != menuTextEntry.getText())
            {
                menuTextEntry.setText(temp);
                JavaWebBrowser.core.getPage(temp);
                menuLoad.setText("Loading 10%");
               
                refresh();
            }
            
                
        }
        
    }
    
    public class BookmarkListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String temp = menuTextEntry.getText();
            for(int i = 0; i<bookmarksLinks.size(); i++)
            {
                if(e.getSource() == bookmarksLinks.get(i))
                    temp = bookmarksLinks.get(i).getText();
            }
            if(temp != menuTextEntry.getText())
            {
                if(temp.startsWith("netnone://"))
                {
                    menuTextEntry.setText(temp);
                    menuLoad.setText("Loading 10%");

                    refreshResource();
                    return;
                }
                menuTextEntry.setText(temp);
                JavaWebBrowser.core.getPage(temp);
                menuLoad.setText("Loading 10%");
               
                refresh();
            }
            
                
        }
        
    }
    private class WindowListener implements WindowStateListener
    {
        public void windowStateChanged(WindowEvent e)
        {
            if(JavaWebBrowser.ui != null)
                JavaWebBrowser.ui.repaint();
            JavaWebBrowser.ui.update(JavaWebBrowser.ui.getGraphics());
        }
    }
    
    private class ConsoleListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == viewConsole)
                JavaWebBrowser.console.showInstance();
            if(e.getSource() == hideConsole)
                JavaWebBrowser.console.hideInstance();
            if(e.getSource() == console)
            {
                viewConsole.setEnabled(false);
                hideConsole.setEnabled(false);
                menuTextEntry.setText("netnone://console");
                menuLoad.setText("Loading 10%");
                refreshResource();
                return;
                
            }
        }
    }
    
    private class bookmarksListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            bookmarksLinks.add(new JMenuItem(menuTextEntry.getText()));
            bookmarksLinks.get(bookmarksLinks.size()-1).addActionListener(new BookmarkListener());
            bookmarks.add(bookmarksLinks.get(bookmarksLinks.size()-1));
            core.WriteBookmarks.WriteBookmarks();
        }
    }
    
    private class RefreshListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(!menuTextEntry.getText().startsWith("netnone://"))
                refresh();
        }
    }
    
    private class BackListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(index > 0)
            {
                main.remove(mainScrollPane.get(index));
                main.add(mainScrollPane.get(index-1),BorderLayout.CENTER);
                index = index -1;
                main.repaint();
            }
        }
    }
    private class ForwardListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(index < mainScrollPane.size()-1)
            {
                main.remove(mainScrollPane.get(index));
                main.add(mainScrollPane.get(index+1),BorderLayout.CENTER);
                index = index + 1;
                main.repaint();
            }
        }
    }
    private class QuitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
        
    }
    
    private class ConfigListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Constants.DIALUP = dialup.isSelected();
            Constants.SLOWMODE = slowmode.isSelected();
            WriteBookmarks.WriteSettings();
        }
        
    }
    
    public void ApplyConfig()
    {
        dialup.setSelected(Constants.DIALUP);
        slowmode.setSelected(Constants.SLOWMODE);
    }
    
    public void addBookmark(String text)
    {
        bookmarksLinks.add(new JMenuItem(text));
        if(bookmarksLinks.size()>0)
        {
            bookmarksLinks.get(bookmarksLinks.size()-1).addActionListener(new BookmarkListener());
            bookmarks.add(bookmarksLinks.get(bookmarksLinks.size()-1));
        }
    }
    
    public void addBookmark(JMenu text)
    {
        bookmarksLinks.add(text);
        if(bookmarksLinks.size()>0)
        {
            bookmarksLinks.get(bookmarksLinks.size()-1).addActionListener(new BookmarkListener());
            bookmarks.add(bookmarksLinks.get(bookmarksLinks.size()-1));
        }
    }
    
    public JMenuItem createBookmark(String text)
    {
        JMenuItem temp = new JMenuItem(text);
        temp.addActionListener(new BookmarkListener());
        bookmarks.add(temp);
        bookmarksLinks.add(temp);
        return temp;
    }
    public void applyBookmarks()
    {
        for(int i = 0; i<bookmarksLinks.size(); i++)
        {
            bookmarksLinks.get(i).addActionListener(new BookmarkListener());
            bookmarks.add(bookmarksLinks.get(i));
        }
    }
    private class DownloadListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try{
                URL temp = new URL(menuTextEntry.getText());
                Console.writeln("starting download " +temp.getFile());
                JEditorPane temp1 = JavaWebBrowser.getCore().getEditorPane();
                String tempJar = temp1.getText();
                Console.writeln(temp.getFile());
                WriteBookmarks.WriteJar(temp.getFile(),tempJar);
                Console.writeln("Writing file "+temp.getFile()+"\n"+tempJar);
                return;
            }catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Download failed!");
            }
        }
    }
    
     private class LinkListener implements HyperlinkListener
    {
        public void hyperlinkUpdate(HyperlinkEvent e)
        {
            try{
                if(e.getDescription() != null && e.getEventType() == HyperlinkEvent.EventType.ENTERED)
                {
                    String text = e.getDescription();
                    menuLoad.setText("Ready  "+text);
                    JavaWebBrowser.ui.repaint();
                }
                else if(e.getDescription() != null && e.getEventType() == HyperlinkEvent.EventType.EXITED)
                {
                    String text = e.getDescription();
                    menuLoad.setText("Ready");
                    JavaWebBrowser.ui.repaint();
                }
                else if(e.getDescription() != null && e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                {
                    
                    String text = e.getDescription();
                    if(text.startsWith("netnone://"))
                    {
                        menuTextEntry.setText(text);
                        menuLoad.setText("Loading 10%");
               
                        refreshResource();
                        return;
                    }
                    if(!text.startsWith("http://") && !text.startsWith("https://"))
                    {
                        if(!extension)
                        {
                            text = historyLinks.get(historyLinks.size()-1).getText()+text;
                            extension = true;
                            extensionLink = historyLinks.size()-1;
                        }
                        else
                            text = historyLinks.get(extensionLink).getText()+text;
                        
                    }
                    else
                    {
                        extension = false;
                    }
                    menuLoad.setText("Loading");
                    JavaWebBrowser.ui.repaint();
                    menuTextEntry.setText(text);
                    JavaWebBrowser.core.getPage(text);
                    menuLoad.setText("Loading 10%");
               
                refresh();
                }
            } catch(NullPointerException ex)
            {
                Console.writeln("Failed");
            }
        }
    }
}
