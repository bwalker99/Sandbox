package ca.ubc.med.ws.middleware.data;

/**
 * Shared object between server and client. 
 * Would normally be in a jar file for one program. Replicated here for simplicity
 * @author rwalker
 *
 */
public class Person {

    private String puid; 
    private String lastname;
    private String firstname;
    private String emailaddress;
	private String cwl;
    
    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getFirstname() {
        return firstname;
    }
    
    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailAddress) {
        this.emailaddress = emailAddress;
    }

    public void setCwl(String cwl) {
		this.cwl = cwl;
		}
	public String getCwl() { return cwl; }	
		
}
