package com.spartans.rentec;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionHelper {
    String ip,db,DBUserNameStr,DBPasswordStr;


    @SuppressLint("NewApi")
    public Connection connectionclasss()
    {

        // Declaring Server ip, username, database name and password
        ip = "192.168.43.3:1433";
        db = "Rentec";
        DBUserNameStr = "sa";
        DBPasswordStr = "Hogwarts@8";
        // Declaring Server ip, username, database name and password


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        java.sql.Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Log.d("firstline","succed");
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Log.d("secondline","succed");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ip +";"+"databaseName="+ db + ";"+"user=" + DBUserNameStr+ ";password=" + DBPasswordStr + ";";
            Log.d("thirdline","succed");
                String Driver=DriverManager.getDriver(ConnectionURL).toString();
            Log.d("Driver",Driver);
               // DriverManager.setLoginTimeout(15);
            connection = DriverManager.getConnection(ConnectionURL);
                        Log.d("forthline","succed");
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}
