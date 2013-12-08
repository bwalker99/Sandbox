package ca.ubc.med.sql;
import java.io.*;
import java.sql.*;
import java.util.*;

//import org.jasig.portal.PortalSessionManager;

/**
 * <p>Title: Jdbc</p>
 * <p>Description: Get a one-time connection object. Database definitions are stored in properties file.
 *    One for each database.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Langara College</p>
 * @author Bob Walker
 * @version 1.0
 */

public final class Jdbc {
  // Where to find the properties files.
  // Change this for Luminis server 
//   private static String propDirectory = "J:\\ics\\icsdev\\java\\data-sources\\";
//   private static String propDirectory = "/usr/local/data-sources/";

  /**
   * Get the database connection.
   * @param database The name of the DSN or database definition properties file.
   * It is assumed there is a file called <propDirectory><database>.props in properties format
   * with the database definitions
   * @return A valid database connection or NULL
   */
public static java.sql.Connection getConnection(String database) {
    java.sql.Connection conn = null;

    try {
      ResourceBundle dbBundle = ResourceBundle.getBundle(database);
      java.util.Properties p = new java.util.Properties();
      Enumeration bundleKeys = dbBundle.getKeys();

      while (bundleKeys.hasMoreElements()) {
        String key = (String)bundleKeys.nextElement();
        String value  = dbBundle.getString(key);
        p.setProperty(key, value);
       }

      String driver = dbBundle.getString("driver");
      String url = dbBundle.getString("url");
      Class classReference = Class.forName(driver);
      java.sql.Driver D = (java.sql.Driver) classReference.newInstance();
      conn = D.connect(url,p);
      conn.setAutoCommit(false);
      conn.setReadOnly(false);
      }
    catch (Exception e) {
      System.out.println("Jdbc:getConnection() - Error creating connection: " + e.getMessage());
      e.printStackTrace();
    }
  return conn;
  }

  public static java.sql.Connection getConnection() {
    return Jdbc.getConnection("DEF");
  }


  public static Connection getConnectionWeb(javax.servlet.ServletContext context) {
    Connection conn = null;
    try {
        // Get global parameters from web.xml
        driver   = context.getInitParameter("driver");
        url      = context.getInitParameter("urlbanner");
        username = context.getInitParameter("username");
        password = context.getInitParameter("password");

      Class.forName(driver).newInstance();
      conn = DriverManager.getConnection(url, username, password);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return conn;
  }
  
/*
  private static void initJDBCParms() {
      try {
        // Get parameters from web.xml
        driver   = PortalSessionManager.getInstance().getServletContext().getInitParameter("driver");
        url      = PortalSessionManager.getInstance().getServletContext().getInitParameter("url");
        username = PortalSessionManager.getInstance().getServletContext().getInitParameter("username");
        password = PortalSessionManager.getInstance().getServletContext().getInitParameter("password");
        urlBanner= PortalSessionManager.getInstance().getServletContext().getInitParameter("urlbanner");
        urlLibrary= PortalSessionManager.getInstance().getServletContext().getInitParameter("urllibrary");        

      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
  }
/*
  public static Connection getConnectionWeb(String dbarea) {
	    String targetUrl;
	    Connection conn = null;
	    initJDBCParms();
	    targetUrl = url;             // set default url to luminis database
	    if (dbarea.equalsIgnoreCase("banner"))
	        targetUrl = urlBanner;
	    if (dbarea.equalsIgnoreCase("library"))
	        targetUrl = urlLibrary;    
	    try {
	      Class.forName(driver).newInstance();
	      conn = DriverManager.getConnection(targetUrl, username, password);
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return conn;
	  }
*/
 
  public static String getUserName()   {
     return username;
  }
 
  // JDBC Connection parameters -- see initJDBCParms()
    private static String driver   = null;
    private static String url      = null;
    private static String username = null;
    private static String password = null;
    private static String urlBanner= null;
    private static String urlLibrary= null;

  
}

