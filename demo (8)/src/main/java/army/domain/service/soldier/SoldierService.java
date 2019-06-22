package army.domain.service.soldier;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import army.domain.model.Soldier;
import army.domain.repository.soldier.SoldierRepository;

import army.domain.repository.soldier.GroupRepository;
import army.domain.model.SoldierGroup;
import army.domain.model.GroupId;

@Service
@Transactional
public class SoldierService {
	
    @Autowired
    GroupRepository groupRepository;
    
    @Autowired
    SoldierRepository soldierRepository;
    
    @Transactional
    public SoldierGroup findByGroupId(int groupId) {
        return groupRepository.findByGroupId(groupId);
    }
    
    @Transactional
    public List<SoldierGroup> findAllGroups(Integer groupId) {
        return groupRepository
                .findByGroupId_groupIdOrderByGroupId_soldierIdAsc(groupId);
    }
    
    @Transactional
	public List<SoldierGroup> findAllGroups() {
		return groupRepository.findAll();
	}
	
    @Transactional
	public List<Soldier> findAllSoldiers()
	{
		return soldierRepository.findAll();	
	}
	
    @Transactional
	public Soldier findSoldier(int id)
	{
		return soldierRepository.findOne(id);
	}
	
    @Transactional
	public void updateSoldier(Soldier soldier)
	{
		//soldierRepository.saveAndFlush(soldier);
	}
    @Transactional
	public void createSoldier(Soldier soldier)
	{
		soldierRepository.saveAndFlush(soldier);
	}
	public void deleteSoldier(Soldier entity) {
		soldierRepository.delete(entity);
	}
	@Transactional
	public SoldierGroup createGroup(Soldier soldier , int Idset, String groupName , String groupTime) {
		SoldierGroup newGroup = new SoldierGroup();
		GroupId groupId = new GroupId();
		groupId.setGroupId(Idset);
		groupId.setSoldierId(soldier.getSoldierId());
		newGroup.setGroupId(groupId);
		newGroup.setGroupName(groupName);
		newGroup.setGroupTime(groupTime);
		
		createSoldier(soldier);
		newGroup.setSoldier(soldier);
		return groupRepository.saveAndFlush(newGroup);
	}
	@Transactional
	public void deleteGroup(SoldierGroup entity) {
		groupRepository.delete(entity);
	}
	@Transactional
	public void deleteAllGroups() {
		groupRepository.deleteAll();
	}
}