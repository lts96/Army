package army.domain.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class GroupId implements Serializable {
	
    private static final long serialVersionUID = 2133924934789863151L;
    
    private Integer groupId;
    
    
    private Integer soldierId;
    public GroupId( Integer groupId,  Integer soldierId ) {
        this.soldierId = soldierId;
        this.groupId = groupId;
    }

    public GroupId() {
    }
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GroupId other = (GroupId) obj;
       
        if (soldierId == null) {
            if (other.soldierId != null)
                return false;
        } else if (!soldierId.equals(other.soldierId))
            return false;
        return true;
    }

    public Integer getSoldierId() {
        return soldierId;
    }

    public void setSoldierId(Integer soldierId) {
        this.soldierId = soldierId;
    }
    
    public void setGroupId(Integer groupId) {
    	this.groupId = groupId;
    }
    public Integer getGroupId()
    {
    	return groupId;
    }

    
}