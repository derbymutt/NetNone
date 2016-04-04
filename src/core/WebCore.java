package core;

import java.awt.BorderLayout;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javawebbrowser.JavaWebBrowser;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class WebCore extends JFrame{
    private JPanel mainPane;
    private JPanel paneCenter;
    private	JTextArea output;
    private JScrollPane textPane;
    private JScrollPane textPane2;
    private final int WIDTH = 280;
    private final int HEIGHT = 540;
    private JFrame mainPane2;
    public JEditorPane editorPane = new JEditorPane();


    private java.net.URL rorServerList;
    private JButton moreHelpButton;

    private HyperlinkListener mouseoverListener;
    private MouseListener mouseListener;

    public String url;

    private boolean event;

    public JEditorPane getEditorPane() {
        return editorPane;
    }
    public WebCore()
     {
             initializePanes();

     }

    private void initializePanes()
    {
        mainPane2 = new JFrame();
        mainPane2.setLayout(new BorderLayout());
        mouseoverListener = new LinkListener();
        mouseListener = new ClickListener();
        getWebStart();
    }


    private void getWebStart()
    {
      // editorPane = FetchPage.get("http://www.google.com/");
       editorPane.addHyperlinkListener(mouseoverListener);
       editorPane.addMouseListener(mouseListener);

    }
    public void getPage(String address)
    {
       editorPane = FetchPage.get(address);
       try{
            url = address;
       }catch(Exception ex){}
    }
    private class ClickListener implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent me) {
            return;
        }

        @Override
        public void mousePressed(MouseEvent me) {
            event = true;
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            event = false;
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            return;
        }

        @Override
        public void mouseExited(MouseEvent me) {
            return;
        }
        
    }
    private class LinkListener implements HyperlinkListener
    {
        public void hyperlinkUpdate(HyperlinkEvent e)
        {
            try{
                if(e.getDescription() != null && e.getEventType() == HyperlinkEvent.EventType.ACTIVATED);
                {
                    String text = e.getDescription();
                    Console.writeln("Got URL "+text+" from click.");
                    if(event)
                    {
                        getPage(text);
                        JavaWebBrowser.ui.editorPane.add(editorPane);
                        JavaWebBrowser.ui.refresh();
                    }
                }
            } catch(NullPointerException ex)
            {
                Console.writeln("Failed");
            }
        }
    }
}


