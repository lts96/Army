package army.domain.model;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Soldier implements Serializable {
	
    private static final long serialVersionUID = 686774175556365789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer soldierId; 
    
    private String soldierPw; 
    
    private String soldierClass;

    private String soldierName;
    
    private String lastTime;   // 사용 시간    원래 log 파일로 받던지 해야되지만 일단 이걸로 
    
    private String soldierPhonenumber; 
    
    private String soldierImei; 
    
    private Integer vacation;
     // 사용할 수 있는 제한들 , 일단 10개로 설정. 
    
    public Soldier() {
    	this.vacation = 1;
    }

    public void setSoldierId(Integer soldierId) {
        this.soldierId = soldierId;
    }
    
    public Integer getSoldierId() {
        return soldierId; 
    }

    public void setSoldierPw(String soldierPw) {
        this.soldierPw = soldierPw;
    }
    
    public String getSoldierPw() {
        return soldierPw;
    }

   
    public void setSoldierName(String name)
    {
    	this.soldierName = name;
    }
    public String getSoldierName() {
    	return this.soldierName;
    }
    public void setSoldierClass(String c)
    {
    	this.soldierClass = c;
    }
    public String getSoldierClass() {
    	return this.soldierClass;
    }
    
    
    /*
    public void setFlag(Integer index, boolean value)
    {
    	this.flag[index] = value;
    }
    
    public boolean getFlag(Integer index)
    {
    	return flag[index];
    }
    */

	public String getSoldierPhonenumber() {
		return soldierPhonenumber;
	}

	public void setSoldierPhonenumber(String soldierPhonenumber) {
		this.soldierPhonenumber = soldierPhonenumber;
	}

	public String getSoldierImei() {
		return soldierImei;
	}

	public void setSoldierImei(String soldierImei) {
		this.soldierImei = soldierImei;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getVacation() {
		return vacation;
	}

	public void setVacation(Integer temp) {
		this.vacation = this.vacation * temp;
	}

	
	

	
   
}
