(function(){


	var customerModule = angular.module('CustomerModule', ['UserModule', 'CouponModule']);


	customerModule.controller('CustomerController', function($scope, $rootScope, $window, CouponsService, UserService) {

		angular.element(document).ready(function() {
			$scope.getAllCoupons();
		});

		$scope.hasChanged = function() {
			$rootScope.myCoupon = $scope.choosenCoupon;
			$window.location.assign("#/custpurchasecoupon");

		}
		$scope.hasChangedCustomerCoupons = function(){
			$rootScope.myCoupon = $scope.choosenCoupon;
			$window.location.assign("#/managecustumercoupons");

		}
		$scope.getAllCoupons = function() {
			CouponsService.getAllCoupons().then(function(response){
				$rootScope.allCoupons = response.data.coupon;
			});

		}

		$scope.getCouponsByCustomerId = function() {
			CouponsService.getCouponsByCustomerId().then(function(response){
				$rootScope.couponsByCustomerId = response.data.coupon;
				$scope.choosenCoupon = $rootScope.couponsByCustomerId[0];
				$window.location.assign("#/showcustomercoupons");

			});

		}

		$scope.purchaseCoupons = function() {
			var confirmation = confirm("Do You Want To Purchase The Coupon?");
			if(confirmation){
				CouponsService.purchaseCoupons($rootScope.myCoupon);
			}
		}

		$scope.removePurchasedCoupon = function() {
			var confirmation = confirm("Remove Purchased Coupon?");
			if(confirmation){
				CouponsService.removePurchasedCoupon($rootScope.myCoupon.id);
			}

		}
		
		$scope.getCouponsByPriceAndCustumerId = function() {
			CouponsService.getCouponsByPriceAndCustumerId(this.choosenPrice).then(function(response){
				$rootScope.couponsByPriceAndCustumerId = response.data.coupon;
				$window.location.assign("#/showcustomercouponsbyprice");

			});

		}	
		$scope.getAllPurchasedCouponsByTypeAndCustumerId = function() {
			CouponsService.getAllPurchasedCouponsByTypeAndCustumerId(this.choosenType).then(function(response){
				$rootScope.allPurchasedCouponsByTypeAndCustumerId = response.data.coupon;
				$window.location.assign("#/showcustomercouponsbytype");

			});

		}


	});

	customerModule.service('CustomerService', function($http) {
		
		this.createCustomer = function(data) {

			$http.post('http://localhost:8080/CouponsProject/rest/customers/', data)			
			.success(function(response) {
				alert(response);
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

		this.updateCusromer = function(data) {

			$http.put('http://localhost:8080/CouponsProject/rest/customers/', data)			
			.success(function(response) {
				alert(response);
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

		this.getCustomer = function(data) {

			return $http.get('http://localhost:8080/CouponsProject/rest/customers/' + data)			
			.success(function(response) {
				return response;
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

		this.removeCustomer = function(data) {
			$http.delete('http://localhost:8080/CouponsProject/rest/customers/' + data)			
			.success(function(response) {
				alert(response);
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

		this.getAllCustomers = function() {

		return $http.get('http://localhost:8080/CouponsProject/rest/customers/allcustomers')			
			.success(function(response) {
				return response;
			})
			.error(function(reponse) {
				alert("ERROR!");
			})
		}

	});




})();