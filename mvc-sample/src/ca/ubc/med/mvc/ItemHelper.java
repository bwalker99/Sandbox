package ca.ubc.med.mvc;
/**
 * Created on Nov 2, 2004
 * Items are typically java beans, containing just data. 
 * ItemHelpers help the beans get, save and delete the data.
 * Aug 2007 BW: Copied to ca.langara.mvc. Added exceptions.  
 */
public abstract class ItemHelper {
public java.sql.Connection conn;	
public String message = null;   // A message to be returned.
public int localid = -1;
public javax.servlet.http.HttpServletRequest request;
protected org.apache.log4j.Logger  logger;
protected Info info;  // The general purpose Info object.
	
public abstract Object get(int index)  throws MVCException;
public abstract Object save(Object O)   throws MVCException;
public abstract void delete(int index)   throws MVCException;

public void setConn(java.sql.Connection C) {
  conn = C;
}
public String getMessage() { 
  return message;
  }
public int getId() { 
	return localid;
}
public void setRequest(javax.servlet.http.HttpServletRequest r) { 
	request = r;
}
public void setLogger(org.apache.log4j.Logger  l ) {
	logger = l;
}
public void setInfo(Info I) {  info = I; }
}
