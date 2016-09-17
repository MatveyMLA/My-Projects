(function(){
	
	
	var couponModule = angular.module('CouponModule',['UserModule']);
	
	couponModule.service('CouponsService', function($http, $window, UserService) {
		
		
		
		this.createCoupon = function(coupon) {
			
			$http.post('http://localhost:8080/Coupons/rest/coupons/' + UserService.user.id, coupon)			
			.success(function(response) {
			})
			.error(function(response) {
				alert(response);
			})
		}
		
		this.updateCoupon = function(coupon) {
			
			$http.put('http://localhost:8080/Coupons/rest/coupons/' + UserService.user.id, coupon)			
			.success(function(response) {
				alert("Coupon Updated.");
				$window.location.assign("#/managecoupon");
			})
			.error(function(response) {
				alert("Can't Update Coupon.");
			})
		}
		
		this.getCouponByCompany = function(couponId) {
			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/' + UserService.user.id + '/' + couponId)		
			.success(function(response) {
				return response;
			})
			.error(function(response) {
				alert("ERROR!");
			})
			
		}
		
		this.removeCoupon = function(couponId) {
			$http.delete('http://localhost:8080/Coupons/rest/coupons/' + couponId)			
			.success(function(response) {
				alert("Coupon Removed");
				$window.location.assign("#/managecoupon");
			})
			.error(function(response) {
				alert("Can't Remove Coupon");
			})
		}
		
		this.removePurchasedCoupon = function(couponId) {
			$http.delete('http://localhost:8080/Coupons/rest/coupons/removepurchasedcoupon/' + couponId + '/' + UserService.user.id)			
			.success(function(response) {
				alert(response);
			})
			.error(function(response) {
				alert(response);
			})
		}
		
		this.getAllCoupons = function() {			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/allcoupons')			
			.success(function(response) {
				return response;
			})
			.error(function(response) {
				alert("Sorry. Try get coupons later.");
			})
			
		}
		
		this.getAllCouponsByCompanyId = function() {
			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/allcouponsbycompanyid/' + UserService.user.id)			
			.success(function(response) {
			})
			.error(function(response) {
				alert("Sorry. Try get coupons later.");
			})
		}
		
		this.getCouponsByType = function(couponType) {
			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/couponsbytype/' + couponType + '/' + UserService.user.id)			
			.success(function(response) {
				return response;
			})
			.error(function(response) {
				alert(response);
			})
		}
		
		this.getCouponsByPrice = function(price) {
			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/couponsbyprice/' + price + '/' + UserService.user.id)			
			.success(function(response) {
			})
			.error(function(response) {
				alert(response);
			})
		}
		
		this.getCouponsByCustomerId = function() {
			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/couponsbycustumerid/' + UserService.user.id)			
			.success(function(response) {
			})
			.error(function(response) {
				alert("Sorry. Try get coupons later.");
			})
		}
		
		this.getCouponsByPriceAndCustumerId = function(price) {
			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/couponsbypriceandcustumerid/' + price + '/' +  UserService.user.id)			
			.success(function(response) {
			})
			.error(function(response) {
				alert(response);
			})
		}
		
		this.getCouponsByDate = function(date) {
			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/couponsbydate/' +  UserService.user.id, date)			
			.success(function(response) {
			})
			.error(function(response) {
				alert(response);
			})
		}
		
		this.getAllPurchasedCouponsByTypeAndCustumerId = function(type) {
			
			return $http.get('http://localhost:8080/Coupons/rest/coupons/couponsbytypeandcustumerid/' + type + '/' +  UserService.user.id)			
			.success(function(response) {
			})
			.error(function(response) {
				alert(response);
			})
		}
		
		this.purchaseCoupons = function(coupon) {
			
			$http.put('http://localhost:8080/Coupons/rest/coupons/purchasecoupon/' + UserService.user.id, coupon)			
			.success(function(response) {
			})
			.error(function(response) {
				alert(response);
			})
		}
	});
})();