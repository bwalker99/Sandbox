package ca.ubc.med.auth;

/*
 * Author: Jane Yi
 * Purpose: To provide commonly used functionalities such as search a LDAP directory, retrieve/add/modify/drop attributes
 * 			  from a LDAP directory
 * Modification Log:
 * Oct 15, 2007 JYI -- moved the program from org.it4bc.util to ca.langara.util to obsolate the it4bc package
 * 						-- In Luminis III, uid is the left most rdn and is also the login name, but in luminis IV, pdsloginid instead of uid is the login name.
 * 							modified the program so that instead of assuming uid is the left most rdn to identify a user and is the login name,  
 * 							it will take a rdnName and a loginName passed in as parameters to be flexiable
 * Jan 20, 2008 JYI -- added procedures to check if a user's account is enabled, and to enable user's account
 *  Mar 3, 2008 BW - Initialize DirContext variable(ctx) when class is instantiated. Does not get set every time.
 *  May 2010 - BW Clean up. Added static definitions.<br/>
 *  Oct 2011 BW  Modified to do read only tasks and work with ANY LDAP. Verified for SUN,eDIR,
 *       OpenLDAP(May still need work with group membership). Not yet verified for AD. 
 *  
 */

import javax.naming.*;
import javax.naming.directory.*;
import javax.servlet.ServletException;

import java.util.*;

public class LDAPLook {

  public static final int LDAP_SUN = 0;
  public static final int LDAP_EDIR = 1;
  public static final int LDAP_AD = 2;      // MS Active Directory
  public static final int LDAP_OPEN = 3;    // Open LDAP - LP 5
  
  
  // Attributes in Luminis LDAP that describes account status 	
  public static final String STATUS_EDIR = "loginDisabled";
  public static final String STATUS_EDIR_TEXT = "FALSE";    // account is called 
  
  public static final String STATUS_LUMINIS = "pdsAccountStatus";
  public static final String STATUS_LUMINIS_TEXT = "enabled";
    
  private String ldapCtxFactory;
  private String ldapUrl;
  private String ldapAuthentication;
  
// These defaults are for eDir. Usually overridden in the properties file. 
  private static String rdnName = "cn";		// the left most relative distingivshed name that identifies a user, 
  private static String loginName = "cn"; 	
  private static String baseDn = "ou=empl,ou=main,o=langara";    // For Sun: ou=People,
  private static String lookupDn = "ou=empl,ou=main,o=langara";  // For Sun: ou=People,o=langara.bc.ca,o=langara
  private static String roleAttribute = "groupMembership";
  private static String displayName = "fullName";
  private int flavour;      // Which LDAP are we accessing. 
 
  DirContext ctx;
  Hashtable<String,String> env;
  java.util.ResourceBundle dbBundle = null;
  
 public static void main (String[] args){

	if (args.length <  2 ) { 
		System.err.println("Usage for testing : LDAP  <LDAPPropFileName> <UserID>");
		System.exit(0);
	 }
	 LDAPLook L = new LDAPLook(args[0]);
	 if (!L.isConnected()) {
		 System.out.println("Connection to LDAP failed");
		 System.exit(1);
	 }	 
	 L.tester(args);
	 L.close();
}
 
 /*
  * Tester program. 
  */
 private void tester(String args[]) {   
 String userid = args[1];
 String loginname = null,password = null;
 if (args.length > 3) { 
	 loginname = args[2];
	 password = args[3];
 }
 
try{

    System.out.println("\nTesting for: " +  userid );
    System.out.println("  Flavour:     " + getFlavour());
    System.out.println("  FoundUser?   " +  findUser(userid));
    System.out.println("  cn         = " + getAttributeValue(loginName,userid,"cn"));
    System.out.println("  fullName   = " + getDisplayName(userid));
    System.out.println("  email      = " + getEmail(userid));
    System.out.println("  Department = " + getDepartment(userid));    
    System.out.println("  Is account enabled? " + isAccountEnabled(userid));
    
    String[] groups = getUserGroups(userid);
    System.out.println("\n  Groups:  " + Arrays.toString(groups));
    System.out.println("\n  Member of 'Group_Staff'? " + memberOf(userid,"Group_Staff"));
    System.out.println("  Member of 'EMPLOYEECURRENT'? " + memberOf(userid,"EMPLOYEECURRENT"));
        
/*
    Attributes answer = ctx.getAttributes(rdnName +  "=" + getRdnValue(loginName,userid) + "," + baseDn);
        for (NamingEnumeration ae = answer.getAll(); ae.hasMore();) {
            Attribute attrib = (Attribute)ae.next();
            System.out.println("attribute: " + attrib.getID());
            // Print each value 
            for (NamingEnumeration e = attrib.getAll(); e.hasMore(); System.out.println("    value: " + e.next()))
        	             ;
        }
	
*/
    
    if (loginname != null) 
    	System.out.println("Testing authentication of " + loginname  + "  " + checkAuthentication(loginname,password));
    	    
 }
    catch (Exception ne)        {
       System.err.println("Error:" + ne.getMessage());
       }
 }

      
  /**
   *  Create instance of class and initiate based on values in properties file. 
   * @param propertyFile
   */ 
  public LDAPLook(String propertyFile) { 
	     connect(propertyFile);	      
	  
  }
  
  public String getFlavour() {
	  String retval = null;;
	  switch (flavour) { 
	  case LDAP_SUN : retval = "SunONE"; break;
	  case LDAP_EDIR : retval = "eDirectory"; break;
	  case LDAP_AD : retval = "ActiveDirectory"; break;
	  case LDAP_OPEN : retval = "OpenLDAP"; break;
	  default : retval = "eDirectory";
	  }
	 return retval;
  }
	  

  // constructor with the name of a property file, will get the enviroment parameters from *.properties 
  public boolean connect(String propertyFile) { 
	  boolean retval = false;
		      
	    try { 
	       dbBundle = java.util.ResourceBundle.getBundle (propertyFile);
	      }
	       catch (java.util.MissingResourceException mre) { 
	    	   System.out.println("Can't get LDAP connection information base on file: " + propertyFile + " : " + mre.getMessage());
	    	   mre.printStackTrace();
	       }

	      java.util.Properties p = new java.util.Properties();
	      Enumeration bundleKeys = dbBundle.getKeys();

	      while (bundleKeys.hasMoreElements()) {
	        String key = (String)bundleKeys.nextElement();
	        String value  = dbBundle.getString(key);
	        p.setProperty(key, value);
	       }

	      System.out.println("Getting bundle values...");
	      
	      ldapCtxFactory = getProperty("ldapCtxFactory");
	      ldapUrl  = getProperty("ldapUrl");
	      ldapAuthentication =  getProperty("ldapAuthentication");
	      String ldapPrincipal = getProperty("ldapPrincipal"); 
	      String ldapCredentials = getProperty("ldapCredentials");
	      	      
	      String temp = getProperty("rdnName");
	      if (temp != null) rdnName = temp; 
	      temp = getProperty("loginName");
	      if (temp != null) loginName = temp; 	      
	      temp = getProperty("baseDn");
	      if (temp != null)  baseDn = temp;
	      temp = getProperty("lookupDn");
	      if (temp != null)  lookupDn = temp;	      	      
	      temp = getProperty("roleAttribute");
	      if (temp != null)  roleAttribute = temp;	      
	      temp = getProperty("displayName");
	      if (temp != null)  displayName = temp;
	      
	      temp =  getProperty("flavour");
	      if (temp == null)  flavour = LDAP_EDIR;
	      else if (temp != null && temp.equalsIgnoreCase("SUN")) flavour = LDAP_SUN;
	      else if (temp != null && temp.equalsIgnoreCase("EDIR")) flavour = LDAP_EDIR;
	      else if (temp != null && temp.equalsIgnoreCase("AD")) flavour = LDAP_AD;
	      else if (temp != null && temp.equalsIgnoreCase("OPENLDAP")) flavour = LDAP_OPEN;

	      env = new Hashtable<String,String>(11);
	      env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);
	      env.put(Context.PROVIDER_URL, ldapUrl);
	      env.put(Context.SECURITY_AUTHENTICATION, ldapAuthentication);
	      env.put(Context.SECURITY_PRINCIPAL, ldapPrincipal);
	      env.put(Context.SECURITY_CREDENTIALS, ldapCredentials);
	      ctx = getDirContext(env);
	      retval = (ctx != null);
	      
	      if (ctx == null) 
	      	System.out.println("Error creating context for: " + ldapUrl);
	      else 
	      	System.out.println("Successfully created context for: " + ldapUrl);
	      return retval;	      
  }

  private String getProperty(String propertyName) { 
	  String retval = null;
	    try { 
		       retval= dbBundle.getString(propertyName);;
		      }
       catch (java.util.MissingResourceException mre) { 
		    	System.out.println("Can't get property for " + propertyName + " " + mre.getMessage());
		       }
       return retval;
  }
  
 /**
  * Check to see if connection to LDAP was successful, or if we are connected. 
  * @return TRUE if connected, FALSE if not. 
  */
public boolean isConnected() { 
	return (ctx != null);
}
  
/**
 * Close the LDAP context. 
 * Typically called from outside this class.
 */
public void close() {
	try {
	  ctx.close();
	} catch (AuthenticationException e) {
	   System.err.println("Error closing LDAP Context:" + e.getMessage());
	}catch (NamingException e) {
	   System.err.println("Error closing LDAP Context:" + e.getMessage());
	   e.printStackTrace();
    }
}

/**
 * Check authentiction for a third party. Uses a separate context and attempts to bind as to that. 
 * @param userid
 * @param password
 * @return
 */
public boolean checkAuthentication(String userid,String password) { 
	boolean retval = false;
	System.out.println("LDAP.checkAuthentication for " + userid );
	String uid = getValue(getAttribute(loginName,userid,rdnName));
	System.out.println("rdnName = " + uid);
	System.out.println("Full DN: " + rdnName + "=" + uid + "," + lookupDn);
	
    Hashtable env2 = new Hashtable(11);
    env2.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);
    env2.put(Context.PROVIDER_URL, ldapUrl);
    env2.put(Context.SECURITY_AUTHENTICATION, ldapAuthentication);
    env2.put(Context.SECURITY_PRINCIPAL,rdnName + "=" + uid + "," + lookupDn);
    env2.put(Context.SECURITY_CREDENTIALS,password);
    DirContext ctx2 = getDirContext(env2);
    if (ctx2 != null) {
    	retval = true;
        try { ctx2.close(); } 
        catch (NamingException ne) { ne.printStackTrace(); }
    }
    
	return retval;
}


/**
 * @return a newly created DirContext based on the supplied connection environment params
 */
public DirContext getDirContext(Hashtable H) {
	DirContext thisctx;
	try {
	  thisctx = new InitialDirContext(H);
	} catch (AuthenticationException e) {
	   System.out.println(this.getClass().getName() + ":LDAP getDirContext::Authen Exception:" + e.getMessage());
       return null;
	}catch (NamingException e) {
	   System.out.println(this.getClass().getName() + ":LDAP getDirContext:: Naming Exception:" + e.getMessage());
	   e.printStackTrace();
       return null;
    }
    return thisctx;
}

/**
 * to check if a user of given value for a given attribute exists in the directory
 * @param idName, the name of the attribute to check on
 * @param idValue, the value to look for
 * @return true if the user identified by the given attribute name and value exist, false otherwise
 */
public boolean findUser(String idValue) {
    Attributes matchAttrs = new BasicAttributes(true); // ignore case
   
    // Find all users that have a valid surname field. (match Surname attribute to not null)
    matchAttrs.put(new BasicAttribute("sn"));
    matchAttrs.put(new BasicAttribute(loginName, idValue));

    // Define the attributes we want returned
    String[] attrIDs = {rdnName,"sn"};
  return findObject(baseDn, matchAttrs,attrIDs);
}

/**
 * Return a list of groups for a user.
 * Notes: Sun LDAP groups are simple. 
 * eDir groups are in the form cn=MyGroup, ou=empl, ou=staff, o=langara
 * The first part of group is returned. eg: MyGroup   
 * @param userid
 * @return A String array of group names. 
 */
public String[] getUserGroups(String userid) {
	  Vector<String> V = new Vector<String>();
	  int startpos = 0;
	  switch (flavour) { 
	    case LDAP_SUN : startpos = 0; break;   // no preamble
	    case LDAP_EDIR : startpos = 3; break;  // in the form of cn=XXXXXX
	    case LDAP_AD : startpos = 3; break;    // ***probably in the form of cn=XXXXXX ????
	    case LDAP_OPEN : startpos = 4; break;    // ***probably in the form of uid=XXXXXX  ????	    
	  default : startpos = 3; break;
	  }
	  	  
    String temp = getAttributeValue(loginName,userid, roleAttribute);
    if (temp == null) 
    	return null;
    
    StringTokenizer st = new StringTokenizer(temp);
    while (st.hasMoreTokens()) {
  	  String temp2 = st.nextToken();
  	  int pos = temp2.indexOf(',');
  	  if (pos < 0)
  		  V.add(temp2.substring(startpos));
  	  else
  		  V.add(temp2.substring(startpos,pos));
    }
   return (String[])V.toArray(new String[V.size()]);
}

public String getDisplayName(String userid) { 
	return getAttributeValue(loginName,userid,displayName);
}

public String getEmail(String userid) { 
	return getAttributeValue(loginName,userid,"mail");
}

public String getDepartment(String userid) { 
	return getAttributeValue(loginName,userid,"l");
}

public boolean memberOf(String userid,String rolename) { 
	return hasValue(userid,roleAttribute,rolename);
}

/**
 * To test if a given user has the requested attribute. 
 * @param idName The name of the attribute that identifies an object.
 * @param idValue The value of the attribute that identifies an object 
 * @param attrName The name of the attribute to check for. 
 * @return true if the user has the given attribute, false otherwise
 */
 public boolean hasAttribute(String idValue, String attrName)  {
  Attributes matchAttrs = new BasicAttributes(true); // ignore case
  matchAttrs.put(new BasicAttribute(loginName, idValue));
  String[] attrIDs = {loginName, attrName};  
  SearchResult sr = getObject(baseDn, matchAttrs, attrIDs);
  if (sr != null)
    return sr.getAttributes().get(attrName) != null;

  return false;
}


/**
 * To test if object of given match attributes exists or not
 * @param name  is the name of the search base dn
 * @param matchAttrs is the matching attributes
 * @param attrIDs is the attributes to be retrived
 * @return true if object of given match attributes exists, false otherwise
 */
private boolean findObject(String name, Attributes matchAttrs, String[] attrIDs)  {
  return (getObject(name, matchAttrs, attrIDs) != null);
}


/** to find a singleton object in the LDAP context
 * @param name  is the name of the search base dn
 * @param matchAttrs is the matching attributes
 * @param attrIDs is the attributes to be retrived
 * @return true if object of given match attributes exists, false otherwise
 */
private SearchResult getObject(String name, Attributes matchAttrs,  String[] attrIDs)  {
   SearchResult sr = null;
   try {
	   // Search for objects that have those matching attributes, returning the attributes we want.
	   NamingEnumeration answer = ctx.search(name, matchAttrs, attrIDs);
     
       if (answer.hasMore()) {  		 
          sr = (SearchResult)answer.next();  
       }
  } 
  catch (AuthenticationException e) {
	    System.out.println(this.getClass().getName() + ":Authen Exception:" + e.getMessage());
	}
  catch (NamingException e) {
	    System.out.println(this.getClass().getName() + ":Get Object Naming Exception:" + e.getMessage());
	    e.printStackTrace();
	}
  catch (Exception e) {
	    e.printStackTrace();
	}
  return sr;
}


/** to find an singleton object in the LDAP context in the given namingspace (dn), cannot go subtree level
 * @param namingspace, the base search dn 
 * @param matchAttrs, the attributes used as searching criteria
 * @param attrIDs, the attributes to retrieve from the directory
 */
private NamingEnumeration getObjects(String namingspace, Attributes matchAttrs, String[] attrIDs)  {
	 NamingEnumeration answer = null;
	 try {
	    // Search for objects that have those matching attributes, returning the attributes we want.
	    answer = ctx.search(namingspace, matchAttrs, attrIDs);
	} catch (AuthenticationException e) {
	    System.out.println(this.getClass().getName() + ":Authen Exception:" + e.getMessage());
	} catch (NamingException e) {
	    System.out.println(this.getClass().getName() + ":Get Object Naming Exception:" + e.getMessage());
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} 
return answer; 
}


/** to find an singleton object in the LDAP context in the base dn, can go subtree level
 * @param base, base search dn
 * @param filter, string in the format of '(attributename=" + attributevalue + ")'
 * @param returnAttrs, attributes to retrieve from the directory
 */
private NamingEnumeration search (String base, String filter, String[] returnAttrs)  {
	 NamingEnumeration answer = null;
	 
	 SearchControls srchControls = new SearchControls();
	 srchControls.setReturningAttributes(returnAttrs);
	 srchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	 srchControls.setReturningObjFlag(true);
	 
	 try {
	    // Search for objects that have those matching attributes, returning the attributes we want.
	    answer = ctx.search(base, filter, srchControls);
	} catch (AuthenticationException e) {
	    System.out.println(this.getClass().getName() + ":Authen Exception:" + e.getMessage());
	} catch (NamingException e) {
	    System.out.println(this.getClass().getName() + ":Get Object Naming Exception:" + e.getMessage());
	} catch (Exception e) {
	    e.printStackTrace();
	} 
 return answer; 
}

/**
 * Get the value of attribute. 
 *  An entry from LDAP is returned as   'displayName:   Bob Walker' 
 *  This method returns the value or data part of the entry.
 * @param attr The attribute to be used.
 * @return The value data from the attribute. 
 */
private  String getValue(Attribute attr) {
	if (attr == null) return null;
   	String temp = attr.toString();  
   	int pos = temp.indexOf(' ');
   	return temp.substring(pos).trim();
}


 /**
  * Return an attribute for the requested name/value pair. 
  * @param idName The name of the attribute that identifies an object.
  * @param idValue The value of the attribute that identifies an object
  * @param attrName The name of the attribute to return. 
  * @return Attribute object of the requested attribute. Null if it does not exist.
  */
  private Attribute getAttribute(String idName, String idValue, String attrName) {
   //	  System.out.println("Searching for " + attrName + " where " + idName + " = " + idValue);
   Attributes matchAttrs = new BasicAttributes(true); // ignore case
   matchAttrs.put(new BasicAttribute(idName, idValue));
   String[] attrIDs = {idName,  attrName};  
   SearchResult sr = getObject(baseDn, matchAttrs, attrIDs);
   if (sr != null)
     return sr.getAttributes().get(attrName);
   else 
	   System.out.println(this.getClass().getName() + ":Error. Can't get attribute (" + attrName + ") for " + idName + "=" + idValue);

   return null;
 }
 
  
  /**
   *  ******* VALIDATE FOR eDIR ***********
   * To test if a given user has the requested value of the requested attribute. 
   * @param idName The name of the id attribute (uid or pdsLoginId, etc...)
   * @param idValue The value of the userid. 
   * @param attrName The name of the attribute to check for. 
   * @param valueName the name of the value to check for
   * @return true if the user has the given attribute, false otherwise
   */
   public boolean hasValue(String idValue, String attrName,String valueName)  {  	 
    //  	 System.out.println(this.getClass().getName() + ":" + idName + ":" + idValue + ":" + attrName + ":" + valueName);  	 
    boolean retval = false;
    Attributes matchAttrs = new BasicAttributes(true); // ignore case
    matchAttrs.put(new BasicAttribute(loginName, idValue));
    String[] attrIDs = {loginName, attrName};  
    SearchResult sr = getObject(baseDn, matchAttrs, attrIDs);
    if (sr != null) {
  	  String temp = getValue( sr.getAttributes().get(attrName));
  	  if (temp != null) 
  	       retval =  temp.toLowerCase().indexOf(valueName.toLowerCase()) >= 0; 
    }
    return retval;
  }
   
   /**
    * Get the value of a user's relative distinguished name attribute. 
    * @param idName The name of the attribute that identifies an user. (The users login id).
    * @param idValue The value of the attribute that identifies an user
    * @return The value of the given user's rdn. 
    */
   public  String getRdnValue(String idName, String idValue) {
  	    return this.getValue(this.getAttribute(idName, idValue, rdnName));
   }
   
   /**
    * Get the value of a specific attribute of a user identified by a given attribute name and value pair. 
    * @param idName The name of the attribute that identifies an user. (The users uid).
    * @param idValue The value of the attribute that identifies an user
    * @param  The name of the attribute to be looked up
    * @return The value of the attribute to be looked up. 
    */
   public  String getAttributeValue(String idName, String idValue, String attriName) {
  	    return this.getValue(this.getAttribute(idName, idValue, attriName));
   }
   
   /**
    * Get the status of a user's eDirectory Account.
    * @param loginValue The value of the attribute that identifies an user
    * @return true is account is enabled, false otherwise 
    */
   public boolean isAccountEnabled(String loginValue) {
	   String accountAttribute = null;  // The name of the attribute that determines account status
	   String accountValue = null;      // The value of the above attribute 
	   
	   switch (flavour) { 
	   		case LDAP_SUN: accountAttribute = STATUS_LUMINIS; accountValue = STATUS_LUMINIS_TEXT; break;
	   		case LDAP_EDIR: accountAttribute = STATUS_EDIR; accountValue = STATUS_EDIR_TEXT; break;
	   		// case LDAP_AD: accountAttribute = STATUS_LUMINIS; accountValue = STATUS_LUMINIS_TEXT; break;
	   		default: accountAttribute = STATUS_EDIR; accountValue = STATUS_EDIR_TEXT; break;
	   }
	   
  	    boolean retval = false;
  	    Attribute attr = getAttribute(loginName, loginValue, accountAttribute);
  	    String accountStatus = null;
  	    if (attr != null) 
  	    	accountStatus = getValue(attr);
  	    if (accountStatus != null)
  	    	return accountStatus.equalsIgnoreCase(accountValue);   // actually disabled. Double entendre....
  	    else 
  	    	return retval;
   }
 
}