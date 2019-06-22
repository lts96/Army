package army.domain.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "usr")       // user는 오직 관리자들로만 구성된다고 가정. 
public class User implements Serializable {
	
    private static final long serialVersionUID = -2649537409906497049L;
    
    @Id
    private String userId;
    
    private String password;
    
    private String IMEI;
    
    private String phoneNumber;
    
    private String name;
    
    // 이걸 방 핀번호로 사용????
    private String groupName;
    
    private String lastUpdate;
    
    private int camera , enable , screen ; 
    
    private int groupPin;
    
    //private Integer[] soldierList;
    
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
    
    
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

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	/*
	public Integer[] getSoldierList() {
		return soldierList;
	}

	public void setSoldierList(Integer[] soldierList) {
		this.soldierList = soldierList;
	}
	*/

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupPin() {
		return groupPin;
	}

	public void setGroupPin(int groupPin) {
		this.groupPin = groupPin;
	}

}