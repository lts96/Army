package army.domain.repository.soldier;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import army.domain.model.SoldierGroup;
import army.domain.model.GroupId;

public interface GroupRepository extends JpaRepository<SoldierGroup, GroupId> {
	
   

	SoldierGroup findByGroupId(GroupId groupId);

	SoldierGroup findByGroupId(int groupId);

	List<SoldierGroup> findByGroupId_groupIdOrderByGroupId_soldierIdAsc(Integer groupId);

    
}