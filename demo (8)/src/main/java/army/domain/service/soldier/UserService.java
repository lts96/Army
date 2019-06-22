package army.domain.service.soldier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import army.domain.model.Soldier;
import army.domain.repository.soldier.SoldierRepository;
import army.domain.repository.soldier.UserRepository;
import army.domain.repository.soldier.GroupRepository;
import army.domain.model.SoldierGroup;
import army.domain.model.User;
import army.domain.model.GroupId;

@Service
@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public List<User> findAllUser()
	{
		return userRepository.findAll();
	}
	public void save(User entity)
	{
		userRepository.save(entity);
	}
	public User findUser(String user_id) {
		
		return userRepository.findOne(user_id);
	}

}
