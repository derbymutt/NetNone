/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author derby_000
 */
public class Definitions {
    public static String DEF_VERSION = "Version";
    public static String DEF_VERSIONPATH = "path";
    public static String DEF_DISABLEITEM = "disable";
    public static String DEF_ENABLEITEM = "enable";
    
    public static final String BUILDNUMBER = "94";
    
    public static ArrayList<String> versionNames = new ArrayList<String>();
    public static ArrayList<String> versionPaths = new ArrayList<String>();
    public static String userPath;
    public static String userDocuments;
    
    public static String currentInputFile="";
    public static String currentInputMap="";
    public static int inputmaplength = 0;

}
