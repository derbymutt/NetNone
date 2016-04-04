/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author derby_000
 */
public class CancelButton extends JButton{
    
    public CancelButton(String text)
    {
        this.addActionListener(new QuitListener());
        this.setText(text);
    }
    
    private class QuitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
        
    }
}
