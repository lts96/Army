package army.domain.model;

public class LoginForm 
{
	private String userId;
	private String password; 
	public LoginForm() {
		
	}
	public LoginForm(String id, String pw) {
		this.setUserId(id); 
		this.setPassword(pw);
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
