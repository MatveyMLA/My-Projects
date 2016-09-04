  (function() {

	var userModule = angular.module('UserModule', []);

	userModule.controller('UserController', function($scope, $rootScope, UserService){
		$rootScope.isShow = false;
		$rootScope.userData = {};
		
		$scope.loginFucntion = function() {
			UserService.loginFunction(this.user).then(function(response) {
				$rootScope.userData = response.data;
				$rootScope.isShow = true;
			});
		}
		
		$scope.companyRegisterFunction = function() {
			UserService.companyRegisterFunction(this.company);
		}

		$scope.customerRegisterFunction = function() {
			UserService.customerRegisterFunction(this.customer);
		}
		
		$scope.logout = function() {
			UserService.logout();
			$rootScope.isShow = false;
		}
	});
	userModule.directive('userInfo', function() {
		  return {
			 templateUrl: 'templates/userTemplates/logoutUserInfo.htm'
		  };
		});
	userModule.service('UserService', function($http, $window) {
		
		var rootObj = this;
		this.user = {
				isLogged: false,
		}
		
		this.loginFunction = function(data) {
			
			return $http.put('http://localhost:8080/CouponsProject/rest/user/', data)			
			.success(function(response) {
				
				if(response.type === "COMPANY"){
					rootObj.user = response;
					$window.location.assign("#/company");
					
				}
				else if(response.type === "CUSTOMER"){
					$window.location.assign("#/customer");
					rootObj.user = response;

				}
				else{
					$window.location.assign("#/admin");
					rootObj.user = response;

				}
				rootObj.user.isLogged = true;
				return rootObj.user;
			})
			.error(function(reponse) {
				alert("ERROR" + reponse.data);
			})

		}
		this.companyRegisterFunction = function(data) {

			$http.post('http://localhost:8080/CouponsProject/rest/user/companyregister', data)
				.success(function(response) {
				alert("Welcome" + data.userName +"!/n" + "Now do Login.");
				$window.location.assign("#/");
			})
			.error(function(reponse) {
				alert(response);
			})
		}
		this.customerRegisterFunction = function(data) {

			$http.post('http://localhost:8080/CouponsProject/rest/user/customerregister', data)
				.success(function(response) {
					alert("Welcome" + data.name +"!\n" + "Now do Login.");
					$window.location.assign("#/");
			})
			.error(function(reponse) {
				alert(reponse);
			})
		}
		this.logout = function() {

			$http.delete('http://localhost:8080/CouponsProject/rest/user/')
				.success(function(response) {
				$window.location.assign("#/");
			})
			.error(function(reponse) {
			})
		}
		this.removeUser = function(user) {
			
			$http.delete('http://localhost:8080/CouponsProject/rest/user/removeuser', user)
			.success(function(response) {
			})
			.error(function(reponse) {
			})
		}
	});
})();