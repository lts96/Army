package army.domain.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;


import javax.persistence.*;

@Entity
public class Management implements Serializable {
	
    private static final long serialVersionUID = 1016314399260689548L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  
    private Integer managementId;
    
  
    private LocalTime startTime;
    
    private LocalTime endTime;
    
    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "soldier_group_id"),
        @JoinColumn(name = "soldier_id") })
    private SoldierGroup soldierGroup;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getManagementId() {
        return managementId;
    }

    public void setManagementId(Integer managementId) {
        this.managementId = managementId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public SoldierGroup getGroup() {
        return soldierGroup;
    }

    public void setGroup(SoldierGroup soldierGroup) {
        this.soldierGroup = soldierGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean overlap(Management target) {
        if (!Objects.equals(soldierGroup.getGroupId(),
                target.soldierGroup.getGroupId())) {
            return false;
        }
        if (startTime.equals(target.startTime)
                && endTime.equals(target.endTime)) {
            return true;
        }
        return target.endTime.isAfter(startTime)
                && endTime.isAfter(target.startTime);
    }

	

}