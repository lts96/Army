package army.domain.model;

public class LoginForm 
{
	private String id;
	private String pw; 
	public LoginForm() {
		
	}
	public LoginForm(String id, String pw) {
		this.setId(id); 
		this.setPw(pw);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
}
