package army.app.soldier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import army.domain.model.GroupId;
import army.domain.model.LoginForm;
import army.domain.model.OutputForm;
import army.domain.model.Query;
import army.domain.model.QueryInput;
import army.domain.model.SearchForm;
import army.domain.model.Soldier;
import army.domain.service.soldier.SoldierService;
import army.domain.service.soldier.UserService;
import army.domain.model.SoldierGroup;
import army.domain.model.User;

//               ok 표시가 들어간 함수들은 기능이 잘 돌아가는 것들 , 구현 완료 표시라고 보면됩니다. 
@CrossOrigin(origins = {"http://localhost:8100","http://localhost:8080" })
@RestController
public class UserController {
	
	
	public static final Object logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    SoldierService soldierService;
    
    @Autowired
    UserService userService;
    
    @ResponseBody
    @RequestMapping(value = "/main/{groupId}/{soldierId}", method = RequestMethod.GET) // 그룹 내 군인 정보 하나를 가져옴 ok
    public ResponseEntity<Soldier> findSoldiers(@PathVariable("groupId")int groupId , // 주의 사항 0부터 시작하기때문에  만약 2번 그룹에 3개가 있을경우 
    		@PathVariable("soldierId")int soldierId ,Model model){					  // main/2/0 main/2/1 main/2/2 이런식으로 접근해야함
    	
    	List<SoldierGroup> soldierGroup = soldierService.findAllGroups(groupId);
    	
    	
    	SoldierGroup group = soldierGroup.get(soldierId);
    	Soldier soldier = group.getSoldier();
    	
    	return new ResponseEntity<Soldier>(soldier, HttpStatus.OK);
    	
    }
    @ResponseBody
    @RequestMapping(value = "/main/{groupId}/{soldierId}", method = RequestMethod.POST) // 그룹 내 군인 정보 하나를 추가함
    public ResponseEntity<Soldier> createSoldiers(@PathVariable("groupId")int groupId , 
    		@PathVariable("soldierId")int soldierId ,@RequestBody Soldier soldier){
    	
    	
    	List<SoldierGroup> soldierGroup = soldierService.findAllGroups(groupId);
    	
    	
    	SoldierGroup group = soldierGroup.get(soldierId);
    	group.setSoldier(soldier);
    	
    	return new ResponseEntity<Soldier>(soldier, HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/main/{groupId}/{soldierId}", method = RequestMethod.DELETE) // 그룹 내 군인 정보 하나를 지움
    public ResponseEntity<Soldier> deleteSoldiers(@PathVariable("groupId")int groupId , 
    		@PathVariable("soldierId")int soldierId ,Model model){
    	
    	List<Soldier> soldierGroup = soldierService.findAllSoldiers();
    	for(int i=0;i<soldierGroup.size();i++)
    	{
    		soldierService.deleteSoldier(soldierGroup.get(i));
    	}
    	if (soldierService.findSoldier(soldierId)!=null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/main/{groupId}/{soldierId}", method = RequestMethod.PUT) // 그룹 내 군인 정보 하나를 갱신
    public ResponseEntity<Soldier> updateSoldiers(@PathVariable("groupId")int groupId , 
    		@PathVariable("soldierId")int soldierId ,@RequestBody Soldier soldier){
    	
    	//List<SoldierGroup> soldierGroup = soldierService.findAllGroups(groupId);
    	Soldier target = soldierService.updateSoldier(groupId, soldier);
    	if(target==null)
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	
    	
    	
    	return new ResponseEntity<Soldier>(target, HttpStatus.OK);
    }
    
    
    
    
    @ResponseBody
    @RequestMapping(value = "/main/{groupId}", method = RequestMethod.GET) // 그룹 하나의 정보를 가져옴    ok
    public ResponseEntity<List<SoldierGroup>> findGroup(@PathVariable("groupId")int groupId ,Model model) {
    	
    	
    	List<SoldierGroup> soldierGroup = soldierService.findAllGroups(groupId);
    	
    	if (soldierGroup==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
    	return new ResponseEntity<List<SoldierGroup>>(soldierGroup, HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/main/", method = RequestMethod.POST) // 그룹 하나 추가 
    public ResponseEntity<SoldierGroup> createGroup(@PathVariable("groupId")int groupId ,@RequestBody SoldierGroup soldierGroup) {
    	
    	
    	((Logger) logger).info("Creating group : {}", soldierGroup);
    	
    	
		if (soldierService.findByGroupId(groupId)!=null) {
			((Logger) logger).error("Unable to create. A Student with name {} already exist", soldierGroup.getGroupId());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
    	return new ResponseEntity<SoldierGroup>(soldierGroup, HttpStatus.OK);
    }
    
    
    
    @ResponseBody
    @RequestMapping(value = "/main/{groupId}", method = RequestMethod.PUT) // 그룹의 정보를 바꿈. (일단은 이름만 테스트) ok   
    public ResponseEntity<List<SoldierGroup>> updateGroup(@PathVariable("groupId")int groupId ,@RequestBody String name) {
    	
    	
    	List<SoldierGroup> soldierGroups = soldierService.findAllGroups(groupId);
    	for(int i=0;i<soldierGroups.size();i++)
    	{
    		soldierGroups.get(i).setGroupName(name);                                     // 그냥 "" 없이 원하는 이름 넣으면 됨.
    	}
    	
    	
    	return new ResponseEntity<List<SoldierGroup>>(soldierGroups, HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/main/{groupId}", method = RequestMethod.DELETE) // 그룹을 통째로 지움. ok
    public ResponseEntity<?> deleteGroup(@PathVariable("groupId")int groupId ,Model model) {
    	
    	
    	List<SoldierGroup> soldierGroups = soldierService.findAllGroups(groupId);
    	for(int i=0;i<soldierGroups.size();i++)
    	{
    		soldierService.deleteGroup(soldierGroups.get(i));
    	}
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/main",method = RequestMethod.GET)  //전체 그룹 정보 전달 ok
    public List<SoldierGroup> listGroups( Model model) {
        List<SoldierGroup> soldierGroups = soldierService.findAllGroups();
        return soldierGroups;
    } 
    @ResponseBody
    @RequestMapping(value = "/main",method = RequestMethod.DELETE)  //전체 그룹 정보 지우기 ok
    public ResponseEntity<?> deletelistGroups( Model model) {
        soldierService.deleteAllGroups();
        return new ResponseEntity<>(HttpStatus.OK);
    } 
   
    
    
    
    
    
    @ResponseBody
    @RequestMapping(value = "/main/sign_in",method = RequestMethod.POST)  // 회원 가입 부분
    public ResponseEntity<OutputForm> changeUserInfo(@RequestBody User user ) {    //user 객체 포맷이랑 정확하게 맞춰서 input 넣어야됨. 
    	List<User> users = userService.findAllUser();
    	OutputForm result = new OutputForm();
    	for(int i=0;i<users.size();i++) {
    		if(users.get(i).getUserId().equals(user.getUserId()))
    		{
    			result.setResult("REGISTER_FAIL_DUPID"); 
    			return new ResponseEntity<OutputForm>(result, HttpStatus.OK);
    		}
    	}
    	userService.save(user);
    	result.setResult("REGISTER_OK"); 
		return new ResponseEntity<OutputForm>(result, HttpStatus.OK);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/main/log_in",method = RequestMethod.PUT)   																			
    public ResponseEntity<OutputForm> login(@RequestBody LoginForm login ) {   // outputform은 string 반환용
    	List<User> users = userService.findAllUser();
    	OutputForm result = new OutputForm();
    	for(int i=0;i<users.size();i++)
    	{
    		if(users.get(i).getUserId().equals(login.getUserId()))
    		{
    			if(users.get(i).getPassword().equals(login.getPassword()))
        		{
    				result.setResult("LOGIN_SUCCESS"); 
        			return new ResponseEntity<OutputForm>(result, HttpStatus.OK);
        		}
    			else 
    			{
    				result.setResult("LOGIN_FAIL, WRONG PW"); 
    				return new ResponseEntity<OutputForm>(result, HttpStatus.OK);
    			}
    		}
    	}
    	result.setResult("LOGIN_FAIL, WRONG ID"); 
    	return new ResponseEntity<OutputForm>(result, HttpStatus.OK);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/main/userlist",method = RequestMethod.GET)  // 유저 정보 확인용  ok 
    public ResponseEntity<List<User>> userList()
    {
    	List<User> users = userService.findAllUser();
    	return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    /*
	@ResponseBody
    @RequestMapping(value = "/main/userGroup",method = RequestMethod.PUT)  // 유저가 관리하는 그룹 출력    이름을 기준으로 함  지금은 id랑 이름을 동기화 시켜서 가능한 것 ok
    public ResponseEntity<List<SoldierGroup>> userGroupList(@RequestBody String user_id)  // 지금은 유저당 그룹 하나를 관리한다고 생각하고 만듬 
    {																					  // 현재 관리하는 그룹 데이터 전체를 보냄 
    	User user = userService.findUser(user_id);
    	List<SoldierGroup> soldierGroups = soldierService.findAllGroups();
    	List<SoldierGroup> userGroup = new ArrayList<SoldierGroup>();
    	
    	for(int i=0;i<soldierGroups.size();i++)
    	{
    		if(user.getGroupName().equals(soldierGroups.get(i).getGroupName())==true)
    		{
    			userGroup.add(soldierGroups.get(i));
    		}
    	}
    	return new ResponseEntity<List<SoldierGroup>>(userGroup, HttpStatus.OK);
    }
    */
	@ResponseBody
    @RequestMapping(value = "/main/userlist/userGroup",method = RequestMethod.PUT)  // 유저가 관리하는 그룹 출력    이름을 기준으로 함  지금은 id랑 이름을 동기화 시켜서 가능한 것 ok
    public OutputForm userGroupList(@RequestBody SearchForm input)  // 지금은 유저당 그룹 하나를 관리한다고 생각하고 만듬 
    {																					  // 현재 관리하는 그룹 이름만 보냄   okok
    	List<SoldierGroup> soldierGroups = soldierService.findAllGroups();				  // put 이라서 user_id를 json으로 보내줘야됨
    	List<SoldierGroup> userGroup = new ArrayList<SoldierGroup>();
    	User user = userService.findUser(input.getUserId());
    	OutputForm result = new OutputForm();
    	for(int i=0;i<soldierGroups.size();i++)
    	{
    		if(user.getGroupName().equals(soldierGroups.get(i).getGroupName())==true)
    		{
    			userGroup.add(soldierGroups.get(i));
    			result.setResult(soldierGroups.get(i).getGroupName()); 
            	return result;
    		}
    	}
    	result.setResult("EMPTY");
    	return result;
    }

	@ResponseBody
    @RequestMapping(value = "/main/userlist/userSoldier",method = RequestMethod.PUT)
	public ResponseEntity<List<Soldier>> userSoldierList(@RequestBody SearchForm input)
	{
		User user = userService.findUser(input.getUserId());
    	List<SoldierGroup> soldierGroups = soldierService.findAllGroups();
    	List<SoldierGroup> userGroup = new ArrayList<SoldierGroup>();
    	List<Soldier> soldiers = new ArrayList<Soldier>();
    	for(int i=0;i<soldierGroups.size();i++)
    	{
    		if(user.getGroupName().equals(soldierGroups.get(i).getGroupName())==true)
    		{
    			userGroup.add(soldierGroups.get(i));
    		}
    	}
    	for(int i=0;i<userGroup.size();i++)
    	{
    		soldiers.add(userGroup.get(i).getSoldier());
    	}
		return new ResponseEntity<List<Soldier>>(soldiers, HttpStatus.OK);
	}
	
	// 이건 쿼리 처리하는 함수 
	
	@ResponseBody
    @RequestMapping(value = "/main/userlist/userQuery",method = RequestMethod.PUT)    // -> 이거 만드는 중이었음 자세한건 txt 요구사항 보고 만들기 
	public ResponseEntity<Query> userManagement(@RequestBody QueryInput queryInput)
	{
		User user = userService.findUser(queryInput.getUser_id());
		Query query = new Query();
		if(user.getRoleName().equals("ADMIN"))                           // 관리자일 경우 제한받지 않으므로 그냥 빠져나감
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
    	long userTime = Long.parseLong(user.getLastUpdate());    // 현재 db 에 있는 유저의 시간 
    	long queryTime = Long.parseLong(queryInput.getLast_update());  // 입력으로 들어온 시간 
    	System.out.println("user Time  : "+ userTime);
    	System.out.println("query Time  : "+ queryTime);
    	if( queryTime > userTime )
    	{
    		user.setLastUpdate(queryInput.getLast_update());
    		query.setUser_id(user.getUserId());
    		query.setLast_update(user.getLastUpdate());
    		query.setControl_time(user.getLastUpdate()+queryInput.getLast_update());
    		query.setIsLastOption(1);
    		
    		System.out.println("query > user");
    	}
    	else 
    	{
    		query.setUser_id(user.getUserId());
    		query.setLast_update(user.getLastUpdate());
    		query.setIsLastOption(0);
    		query.setControl_time(queryInput.getLast_update()+user.getLastUpdate());
    		query.setLast_update(user.getLastUpdate());
    		query.setCamera(user.getCamera());
    		query.setEnable(user.getEnable());
    		query.setScreen(user.getScreen());
    		System.out.println("query <= user");
    	}
    	
    	
		return new ResponseEntity<Query>(query, HttpStatus.OK);
	}
}