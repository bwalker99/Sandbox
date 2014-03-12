package ca.ubc.med.middleware.data;

import javax.xml.bind.annotation.XmlRootElement;

import ca.ubc.med.middleware.data.DataObject;

import java.io.Serializable;

@XmlRootElement(name = "Person")

public class Person extends DataObject implements Serializable {

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
