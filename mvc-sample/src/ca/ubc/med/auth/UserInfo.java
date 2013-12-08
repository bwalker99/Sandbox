package ca.ubc.med.auth;

public class UserInfo {
	private String userid;
	private String username;  // Display name	
	private boolean admin;
	private java.sql.Date displayDate;
	
	public UserInfo() { 
		admin = false;		
	}

	public UserInfo(String userid,String username,javax.servlet.http.HttpServletRequest request) {
		this.userid = userid;
	    this.username = username;
	    
		admin = false;
		
		displayDate = new java.sql.Date(System.currentTimeMillis());
		setValues(request);

	}		


	public void setValues(javax.servlet.http.HttpServletRequest request) { 
		String temp = request.getParameter("admin");
		
		if (temp != null)
			admin = setAdmin(temp);
	}


	public void setUsername(String u) { 
		username = u;
	}

	public void setUserid(String u) { 
		userid = u;
	}

	private boolean setAdmin(String a) { 
		if (a.equalsIgnoreCase("Y"))
			return true;
		else 
			return false;
	}

	public void setDisplaydate(java.sql.Date displayDate) { 
		this.displayDate = displayDate;
	}

	public java.sql.Date getDisplayDate() { 
		return displayDate;	
	}

	public String getUserid() { 
		return userid;
	}

	public String getUsername() { 
		return username;
	}
	public boolean getAdmin() { 
		return admin;
	}

	public String toString() { 
	  String retval = "\nUserInfo\n"; 
	  retval += "  Username=" + username + "\n";
	  retval += "  Admin=" + admin + "\n";	 
	  return retval;
	}



}
