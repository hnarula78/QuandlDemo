package com.quandl.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by himan on 2017-02-03.
 */
public class Utilities {

    String chromedriverpath = null;
    String userName = null;
    String password = null;
    String email = null;


    public void loadProperties()
    {
        Properties properties = new Properties();


        try{
            InputStream input = new FileInputStream("custom.properties");
            properties.load(input);

            chromedriverpath = properties.getProperty("chromedriverpath");
            userName = properties.getProperty("signUpUser");
            email = properties.getProperty("signUpEmail");
            password = properties.getProperty("signUpPassword");

        }catch(IOException ioex)
        {
            ioex.printStackTrace();
        }

    }

    public String getChromeDriverLocation()
    {
        return chromedriverpath;

    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }
}
