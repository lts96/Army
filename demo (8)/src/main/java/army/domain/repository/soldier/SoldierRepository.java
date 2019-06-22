package army.domain.repository.soldier;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import army.domain.model.Soldier;

public interface SoldierRepository extends JpaRepository<Soldier , Integer > {
	
    Soldier findBySoldierId(@Param("soldier_Id") Integer soldierId);
    
}
