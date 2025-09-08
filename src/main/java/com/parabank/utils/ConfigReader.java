package com.parabank.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop = new Properties();
    static {
        try (InputStream in = new FileInputStream("src/test/resources/config.properties")) {
            prop.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
    public static String get(String key){ return prop.getProperty(key); }
    public static boolean getBoolean(String key){ return Boolean.parseBoolean(get(key)); }
    public static int getInt(String key, int def){ try{return Integer.parseInt(get(key).trim());}catch(Exception e){return def;} }
}
