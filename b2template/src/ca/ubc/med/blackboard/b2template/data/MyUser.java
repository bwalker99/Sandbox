package ca.ubc.med.blackboard.b2template.data;

/**
 * A data bean to store information about a User. <br/>
 * Note that we could use a Blackboard user object. Just using this as an example as to how to use and create our own object. <br/>
 * @author rwalker
 *
 */
public class MyUser {

		private String username;
		private String firstname;
		private String lastname;
		private String email;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		  
}
