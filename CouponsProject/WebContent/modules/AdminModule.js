(function(){


	var adminModule = angular.module('AdminModule',['CompanyModule', 'CustomerModule', 'UserModule']);


	adminModule.controller('AdminController', function($scope, $rootScope, $window, CompanyService, CustomerService, UserService) {

		$scope.hasChangedCompanies = function() {
			$rootScope.myCompany = $scope.choosenCompany;
			$window.location.assign("#/admincompanymanagment");
		}
		$scope.hasChangedCustomers = function(){
			$rootScope.myCustomer = $scope.choosenCustomer;
			$window.location.assign("#/admincustomermanagment");
		}
		angular.element(document).ready(function() {
			$scope.getAllCompanies();
			$scope.getAllCustomers();
		});

		$scope.createCompany = function(){
			CompanyService.createCompany(this.company);
		}
		$scope.createCustomer = function(){
			CustomerService.createCustomer(this.customer);
		}
		$scope.updateCompany = function() {
			var confirmation = confirm("Confirm Update");
			if(confirmation){
				CompanyService.updateCompany(this.company);
			}
		}
		$scope.updateCustomer = function(){
			var confirmation = confirm("Confirm Update");
			if(confirmation){
				CustomerService.updateCusromer($rootScope.myCustomer);
			}
		}
		$scope.getCompany = function() {
			CompanyService.getCompany(this.companyId);
		}
		$scope.getCustomer = function(){
			CustomerService.getCustomer(this.customerId);
		}
		$scope.getAllCompanies = function() {
			CompanyService.getAllCompanies().then(function(response){
				$rootScope.allCompanies = response.data.company;
			});
		}
		$scope.getAllCustomers = function(){
			CustomerService.getAllCustomers().then(function(response){
				$rootScope.allCustomers = response.data.customer;
			});
		}
		$scope.removeCompany = function() {
			var confirmation = confirm("Confirm Delete?");
			if(confirmation){
				CompanyService.removeCompany($rootScope.myCompany.id);
			}
		}
		$scope.removeCustomer = function(){
			var confirmation = confirm("Confirm Delete?");
			if(confirmation){
				CustomerService.removeCustomer($rootScope.myCustomer.id);
			}
		}
		$scope.removeUser = function(){
			var confirmation = confirm("Confirm Delete?");
			if(confirmation){
				UserService.removeUser(this.user);
			}
		}

	});

})();