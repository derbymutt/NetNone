/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javawebbrowser;

import core.Console;
import core.WebCore;
import userInterface.UI;

/**
 *
 * @author derby_000
 */
public class JavaWebBrowser {

    public static WebCore core;
    public static UI ui;
    public static Console console;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      // Console console = new Console();
        core = new WebCore();
        console = new Console();
        console.hideInstance();
        ui = new UI();
    }

    public static UI getUi() {
        return ui;
    }

    public static WebCore getCore() {
        return core;
    }
    
}
