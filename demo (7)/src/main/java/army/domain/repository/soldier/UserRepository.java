package army.domain.repository.soldier;

import org.springframework.data.jpa.repository.JpaRepository;

import army.domain.model.GroupId;
import army.domain.model.SoldierGroup;
import army.domain.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
