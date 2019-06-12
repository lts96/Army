angular.module('app.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider
    

  .state('welcomeslide', {
    url: '/slide',
    templateUrl: 'templates/welcomeslide.html',
    controller: 'welcomeslideCtrl'
  })

  .state('login', {
    url: '/login',
    templateUrl: 'templates/login.html',
    controller: 'loginCtrl'
  })

  .state('signup', {
    url: '/signup',
    templateUrl: 'templates/signup.html',
    controller: 'signupCtrl'
  })

  .state('main', {
    url: '/main',
    templateUrl: 'templates/main.html',
    controller: 'mainCtrl'
  })

  .state('soldierlist', {
    url: '/soldierlist',
    templateUrl: 'templates/soldierlist.html',
    controller: 'soldierlistCtrl'
  })

  .state('groupsoldierlist', {
    url: '/page14',
    templateUrl: 'templates/groupsoldierlist.html',
    controller: 'groupsoldierlistCtrl'
  })

  .state('individualmanage', {
    url: '/individualmanage/:soldierId',
    templateUrl: 'templates/individualmanage.html',
    controller: 'individualmanageCtrl'
  })

  .state('delegation', {
    url: '/delegation',
    templateUrl: 'templates/delegation.html',
    controller: 'delegationCtrl'
  })

  .state('setting', {
    url: '/setting',
    templateUrl: 'templates/setting.html',
    controller: 'settingCtrl'
  })

  .state('groupmanage', {
    url: '/groupmanage',
    templateUrl: 'templates/groupmanage.html',
    controller: 'groupmanageCtrl'
  })

  .state('firstpage', {
    url: '/firstpage',
    templateUrl: 'templates/firstpage.html',
    controller: 'firstpageCtrl'
  })

$urlRouterProvider.otherwise('/slide')


});