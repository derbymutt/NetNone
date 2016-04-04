/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.Color;

/**
 *
 * @author derby_000
 */
public class Constants {
    public static final Color BACKGROUNDBLUE = new Color(200,221,242);
    
    public static final String EN_US = "English (U.S.)";
    public static final String EN_UK = "English (U.K.)";
    public static final String ES_IN = "Spanish (International)";
    public static final String[] LANGUAGES = { EN_US, EN_UK , ES_IN};
    public static final String TEXTLANGUAGE = "Language:";
    
    public static final String GEARBOX_AUTO = "Automatic Shift";
    public static final String GEARBOX_MANAUTO = "Manual Shift - Automatic Clutch";
    public static final String GEARBOX_MANSEQ = "Fully Manual: Sequential Shift";
    public static final String GEARBOX_MAN = "Fully Manual: Stick Shift";
    public static final String GEARBOX_MANRANGE = "Fully Manual: Stick Shift with Ranges";
    public static final String[] GEARBOXES = { GEARBOX_AUTO, GEARBOX_MANAUTO, GEARBOX_MANSEQ, GEARBOX_MAN, GEARBOX_MANRANGE };
    public static final String TEXTGEARBOX = "Default Gearbox Mode:";
    
    public static final String TEXTFOVINT = "Field of View - Internal";
    public static final String TEXTFOVEXT = "Field of View - External";
    
    public static final String TEXTCAMERAPITCH = "Disable Camera Pitching";
    public static final String TEXTARCADE = "Arcade Controls";
    public static final String TEXTCREAKSOUND = "Disable Creak Sound";
    
    public static final String TEXTUSERTOKEN = "User Token:";
    public static final String TEXTGETUSERTOKEN = "Get Token";
    
    public static final String SOFTWARETITLE = "Rigs of Rods configuration";
    
    public static final String FILTERING_0 = "None (fastest)";
    public static final String FILTERING_1 = "Bilinear";
    public static final String FILTERING_2 = "Trilinear";
    public static final String FILTERING_3 = "Anisotropic (best looking)";
    public static final String[] FILTERINGS = {FILTERING_0, FILTERING_1, FILTERING_2, FILTERING_3};
    public static final String TEXTFILTERING = "Texture Filtering:";
    
    
    public static final String JDKVERSION = "JDK 1.8";
    
        
    public static final String SERVER_HOSTNAME = "Server Host Name:";
    public static final String SERVER_PORT = "Server Port Number:";
    public static final String SERVER_PASSWORD = "Server Password:";
    
    public static final int FILE_CONFIG = 0;
    public static final int FILE_MAPPING = 1;
    
    public static boolean DIALUP = true;
    public static boolean SLOWMODE = true;
}
