(function() {

	var companyModule = angular.module('CompanyModule',['UserModule', 'CouponModule']);



	companyModule.controller('CompanyController', function($scope, $rootScope, $window, CouponsService, UserService) {


		$scope.hasChanged = function() {
			$rootScope.myCoupon = $scope.choosenCoupon;
			$window.location.assign("#/managecoupon");

		}

		$scope.createCoupon = function(){
			var confirmation = confirm("Create Coupon?");
			if(confirmation){
				CouponsService.createCoupon(this.coupon);

			}
		}
		$scope.updateCoupon = function() {
			var confirmation = confirm("Confirm Update Coupon");
			if(confirmation){
				CouponsService.updateCoupon($rootScope.myCoupon);	
			}
		}
		$scope.getCouponByCompany = function() {
			CouponsService.getCouponByCompany().then(function(resopnse){
				$scope.oneCouponByCompany = response;
			});

		}
		$scope.removeCoupon = function() {
			var confirmation = confirm("Confirm Delete?");
			if(confirmation){
				CouponsService.removeCoupon($rootScope.myCoupon.id);
			}
		}
		
		$scope.getAllCouponsByCompanyId = function() {
			CouponsService.getAllCouponsByCompanyId().then(function(response){
				$rootScope.allCouponByCompanyId = response.data.coupon;
				$window.location.assign("#/showcoupons");
			});

		}
		$scope.getCouponsByType = function() {
			CouponsService.getCouponsByType(this.choosenType).then(function(response){
				$rootScope.couponsByType = response.data.coupon;
				$window.location.assign("#/showcouponsbytype");

			});

		}
		$scope.getCouponsByPrice = function() {
			CouponsService.getCouponsByPrice(this.choosenPrice).then(function(response){
				$rootScope.couponsByPrice = response.data.coupon;
				$window.location.assign("#/showcouponsbyprice");

			});
		}
		
		
		$scope.getCouponsByDate = function() {
			CouponsService.getCouponsByDate(this.date).then(function(response){
				$scope.couponsByDate = response;

			});

		}
	});

	companyModule.service('CompanyService', function($http) {

		this.createCompany = function(data) {

			$http.post('http://localhost:8080/CouponsProject/rest/companies/', data)			
			.success(function(response) {
				alert(response);
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

		this.updateCompany = function(data) {

			$http.put('http://localhost:8080/CouponsProject/rest/companies/', data)			
			.success(function(response) {
				alert(response);
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

		this.getCompany = function(data) {

			return $http.get('http://localhost:8080/CouponsProject/rest/companies/' + data)			
			.success(function(response) {
				return response;
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

		this.removeCompany = function(data) {
			$http.delete('http://localhost:8080/CouponsProject/rest/companies/' + data)			
			.success(function(response) {
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

		this.getAllCompanies = function() {

			return $http.get('http://localhost:8080/CouponsProject/rest/companies/allcompanies')			
			.success(function(response) {
				return response;
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}



	});

})();