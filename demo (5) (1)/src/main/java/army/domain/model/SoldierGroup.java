package army.domain.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class SoldierGroup implements Serializable {
	
    private static final long serialVersionUID = 957780691869519467L;

    @EmbeddedId
    private GroupId groupId;
    
    @ManyToOne
    @JoinColumn(name = "soldier_id", insertable = false, updatable = false)
    @MapsId("soldierId")
    private Soldier soldier; // 관리자 + 일반 유저들
    
    private String groupName;
    
    private String groupTime;   // 필요없어지면 지워버리기 
    
    public SoldierGroup(GroupId groupId) {
        this.groupId = groupId;
    }

    public SoldierGroup() {
    }

    public GroupId getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupId groupId) {
        this.groupId = groupId;
    }

    public Soldier getSoldier() {
        return soldier;
    }
    public void setSoldier(Soldier soldier) {
    	this.soldier = soldier;
    }

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupTime() {
		return groupTime;
	}

	public void setGroupTime(String groupTime) {
		this.groupTime = groupTime;
	}
}