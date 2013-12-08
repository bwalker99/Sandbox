package ca.ubc.med.mvc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * A singleton static class to hold destination URLS by event
 * as defined in the Urls.properties file.
 */
public class DispatchUrls {

private static HashMap urls = null;
private static DispatchUrls instance = new DispatchUrls();
private static String filename = "Urls";
	  
private DispatchUrls() { 
}

public static DispatchUrls getInstance() {
	return instance;
}

public void load() {
	urls = new HashMap();
    ResourceBundle bundle = ResourceBundle.getBundle (filename);
    Enumeration e = bundle.getKeys();
    while (e.hasMoreElements()) {
      String key = (String) e.nextElement(); 
      String value = bundle.getString(key);
      urls.put(key,value);
//      System.out.println("Urls:" + key + "=" + value);
    }
}

public void load(String file) { 
	filename = file;
	load();
}

public static String getUrl(String action) {
  if (action == null) return null;
  if (urls == null) return null;
	return (String)urls.get(action);
 }
}
