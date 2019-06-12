package army.domain.model;

public class Query 
{
	private String user_id; 
	private String last_update; 
	private int isLastOption, camera, enable , screen; 
	private String control_time;
	public Query() {
		
	}
	public Query(String id , String time)
	{
		this.setUser_id(id); 
		this.setLast_update(time);
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getLast_update() {
		return last_update;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	public int getCamera() {
		return camera;
	}
	public void setCamera(int camera) {
		this.camera = camera;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public int getScreen() {
		return screen;
	}
	public void setScreen(int screen) {
		this.screen = screen;
	}
	public String getControl_time() {
		return control_time;
	}
	public void setControl_time(String control_time) {
		this.control_time = control_time;
	}
	public int getIsLastOption() {
		return isLastOption;
	}
	public void setIsLastOption(int isLastOption) {
		this.isLastOption = isLastOption;
	}
	
}
