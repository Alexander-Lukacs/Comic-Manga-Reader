package com.example.besitzer.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * This is a Class that contains every interesting information about an Opened State
 *
 * The purpose of this class is to marshall between the Database, the Logic and the GUI,
 * But also to provide some helpful functions.
 */

public class Opened {
    public static final int UNREAD = 0;
    public static final int PARTIALLY_READ = 1;
    public static final int READ = 2;
    public static final int UNREADABLE = 3;
}
