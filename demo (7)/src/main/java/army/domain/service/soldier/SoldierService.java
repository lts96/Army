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
    
    public SoldierGroup findByGroupId(int groupId) {
        return groupRepository.findByGroupId(groupId);
    }
    public List<SoldierGroup> findAllGroups(Integer groupId) {
        return groupRepository
                .findByGroupId_groupIdOrderByGroupId_soldierIdAsc(groupId);
    }
    
	public List<SoldierGroup> findAllGroups() {
		return groupRepository.findAll();
	}
	
	public List<Soldier> findAllSoldiers()
	{
		return soldierRepository.findAll();	
	}
	
	
	public Soldier findSoldier(int id)
	{
		return soldierRepository.findOne(id);
	}
	
	public Soldier updateSoldier(int groupId, Soldier soldier)
	{
		//groupRepository.findByGroupId(groupId).getSoldier() = soldier;
		return groupRepository.findByGroupId(groupId).getSoldier();
		
	}
	public void createSoldier(int groupId,Soldier soldier)
	{
		//groupRepository.findByGroupId(groupId).setSoldier(soldier);
		soldierRepository.saveAndFlush(soldier);
	}
	public void deleteSoldier(Soldier entity) {
		soldierRepository.delete(entity);
	}

	public void createGroup(SoldierGroup entity ) {
		SoldierGroup newGroup = new SoldierGroup();
		newGroup = entity;
		groupRepository.saveAndFlush(newGroup);
	}
	public void deleteGroup(SoldierGroup entity) {
		groupRepository.delete(entity);
	}
	public void deleteAllGroups() {
		groupRepository.deleteAll();
	}
}