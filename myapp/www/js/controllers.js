angular.module('app.controllers', [])

.controller('welcomeslideCtrl', ['$scope', '$stateParams','$state', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams,$state) {
    console.log("skipStatus : "+localStorage.firststate);
    $scope.skip=function(){
        if(localStorage.firststate==true){
            $state.go('firstpage',{},{reload:'firstpage'});
        }
    };
}])
   
.controller('loginCtrl', ['$scope', '$stateParams', '$state', '$http','$q', 'AccountService', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams, $state, $http, $q, AccountService) {
    $scope.user={};

    $scope.compare=function(){
        console.log($scope.user.userId);
        console.log($scope.user.password);
        let body={
            userId:$scope.user.userId,
            password:$scope.user.password
        };
        let url="http://221.155.56.120:8080/main/log_in";
        $http.put(url, body, {headers : { 'Content-type': 'application/json' }}).then(function(res) {
            console.log("res:"+JSON.stringify(res));
            let response=res.data;
            if(response.result=="LOGIN_SUCCESS"){
                AccountService.setAccount($scope.user.userId);
                sessionStorage.id=$scope.user.userId;
                $http.put("http://221.155.56.120:8080/main/userlist/userGroup", {'userId':sessionStorage.id}, {
                    headers : { 
                        'Content-type': 'application/json' 
                    }}).then(function(res) {
                    let response=res.data;
                    console.log(response.result);
                    sessionStorage.groupname=response.result;
                });
                $state.go('main',{},{reload:'main'});
            }
            else{
            alert("아이디나 비밀번호가 틀렸습니다.");
            // location.reload();
            }
        });
    };

}])

.controller('signupCtrl', ['$scope', '$stateParams', '$state', '$http','$q', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams, $state, $http, $q) {
    $scope.usersign={};

    $scope.signup=function(){
        // console.log($scope.usersign.userId);
        // console.log($scope.usersign.password);
        // console.log($scope.usersign.name);
        // console.log($scope.usersign.groupName);
        // console.log($scope.usersign.phoneNumber);
        if($scope.usersign.password==$scope.usersign.pwconfig){
            if($scope.usersign.userId&&$scope.usersign.password&&$scope.usersign.phoneNumber&&$scope.usersign.name&&$scope.usersign.groupName){
                let body={
                    userId:$scope.usersign.userId,
                    password:$scope.usersign.password,
                    phoneNumber:$scope.usersign.phoneNumber,
                    name:$scope.usersign.name,
                    groupName:$scope.usersign.groupName,
                    lastUpdate:"0",
                    camera:"0", 
                    nable:"0", 
                    screen:"0",
                    roleName:"ADMIN",
                    imei:"0",
                    groupPin:"0"
                };
                let url="http://221.155.56.120:8080/main/sign_in";
                $http.post(url, body, {headers : { 'Content-type': 'application/json' }}).then(function(res) {
                    console.log("res:"+JSON.stringify(res));
                    let response=res.data;
                    if(response.result=="REGISTER_OK"){
                    alert("회원가입이 정상적으로 완료되었습니다.");
                    $state.go('login');
                    }
                    else{
                    alert("아이디가 이미 존재합니다.");
                    // location.reload();
                    }
                });
            }
            else{
                alert("양식을 모두 채워주세요.");
            }
        }
        else{
            alert("비밀번호를 다시 확인하세요.");
        }
    };

}])
   
.controller('mainCtrl', ['$scope', '$stateParams', 'AccountService','$state', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams,AccountService,$state) {
    $scope.idrefresh=function(){
        var user=sessionStorage.id;
        $scope.userid=user;
    }
    $scope.logout=function(){
        sessionStorage.clear();
        $state.go('login',{},{reload: 'login'});
    }
    $scope.golist=function(){
        $state.go('soldierlist',{},{reload:'soldierlist'});
    }
    $scope.gogroup=function(){
        $state.go('groupsoldierlist',{},{reload: 'groupsoldierlist'});
    }
    $scope.gosetting=function(){
        $state.go('setting',{},{reload: 'setting'});
    }
}])

.controller('soldierlistCtrl', ['$scope', '$stateParams', 'SoldierService','$http', 'AccountService', '$state',// The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams, SoldierService, $http, AccountService, $state) {
    $scope.group=sessionStorage.groupname;
    console.log("이름:"+sessionStorage.groupname);
    $scope.data = {
        showDelete: false
    };

    $scope.onItemDelete = function(soldier) {
        var UP;
        UP=confirm("정말 사용자를 삭제하시겠습니까?")
        if(UP){
            // $scope.items.splice($scope.items.indexOf(item), 1);
            alert("사용자를 삭제하였습니다.");
            // location.reload();
            $state.go('soldierlist',{},{reload: 'soldierlist'});
        }
    };

    $scope.clickSoldier = function(soldier) {
        // console.log(this.)
    };

    SoldierService.getSoldiers().then(function(res){
        $scope.soldiers=res;
    });

    $scope.doRefresh = function() {
        SoldierService.getSoldiers().then(function(res){
            $scope.soldiers=res;
        });
        $scope.$broadcast('scroll.refreshComplete');
    };
}])
   
.controller('groupsoldierlistCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams) {


}])
   
.controller('individualmanageCtrl', ['$scope', '$stateParams', 'SoldierService','$http', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams, SoldierService,$http) {

    console.log("stateparams : "+JSON.stringify($stateParams));
	$scope.soldier = {};
	
	// SoldierService.getSoldier($stateParams.userId).then(function(res) {
	// 	$scope.soldier = res;	
	// });
    $scope.activeSection=1;
    $scope.changeSection=function(s){
        $scope.activeSection=s;
    }
    $scope.dv = {
        'dvtext':'위반목록'
    }
    $scope.confirm=function(){
        var UP;
        UP=confirm("경고! 정말 종료요청을 보내시겠습니까?");
        if(UP){
            alert("기기에 강제 종료 요청을 보냈습니다.");
        }
    }
    $scope.alert=function(){
        alert("기기에 푸쉬알림을 보냈습니다.");
    }
    
}])
   
.controller('delegationCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams) {


}])
   
.controller('settingCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams) {
}])
   
.controller('groupmanageCtrl', ['$scope', '$stateParams', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams) {


}])
   
.controller('firstpageCtrl', ['$scope', '$stateParams','$state', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
function ($scope, $stateParams, $state) {
    localStorage.setItem('firststate', true);
    console.log("skipStatus : "+localStorage.firststate);
}])
 