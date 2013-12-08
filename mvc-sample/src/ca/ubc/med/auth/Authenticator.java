package ca.ubc.med.auth;

import javax.servlet.http.HttpServletRequest;

public interface Authenticator {
  public boolean authenticate();
  public void init(HttpServletRequest request);
  public String getUserid(); 
  public String getUsername();
}
