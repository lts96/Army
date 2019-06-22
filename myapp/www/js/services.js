angular.module('app.services', [])

.factory('BlankFactory', [function(){

}])

.service('AccountService', [function() {
    var data={};

    this.setAccount = function (userId) {
        data={
            'userId':userId
        };
        console.log("setid : "+JSON.stringify(data));
    }
    this.getAccount = function () {
        console.log("getid : "+JSON.stringify(data.userId));
        return data.userId;
    }
}])

.factory('SoldierService', ['$http','$q','AccountService',function($http,$q,AccountService) {

    //v1
    return{
        getSoldiers:function() {
            var deferred = $q.defer();
                    
            $http.put("http://221.155.56.120:8080/main/userlist/userSoldier",{'userId':sessionStorage.id}).then(function(res) {
                var soldiers=res.data.map(function(soldier){
                    // soldier.id=soldier.url;
                    console.dir("soldier="+JSON.stringify(soldier));
                    // sessionStorage.currentsoldier=soldier;
                    return soldier;
                });
                deferred.resolve(soldiers);
            });
            return deferred.promise;
        },

        getSoldier:function(url){
            var deferred=$q.defer();

            $http.get(url).then(function(res){
                console.dir("res.data="+JSON.stringify(res.data));
                deferred.resolve(res.data);
            });
            return deferred.promise;
        }
    };
}])

.service('BlankService', [function(){
    
}]);