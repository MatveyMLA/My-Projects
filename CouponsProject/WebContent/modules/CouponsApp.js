(function(){

	var couponsApp = angular.module('CouponsApp', ['UserModule', 'CompanyModule','AdminModule', 
	                                               'CouponModule', 'CustomerModule' ,'ngRoute']);

	couponsApp.config(['$routeProvider', function($routeProvider){

		$routeProvider
		.when("/", {
			templateUrl : "templates/userTemplates/login.htm"
		})
		.when("/register", {
			templateUrl : "templates/userTemplates/register.htm"
		})
		.when("/companyregister", {
			templateUrl : "templates/userTemplates/companyRegister.htm"
		})
		.when("/customerregister", {
			templateUrl : "templates/userTemplates/customerRegister.htm"
		})
		.when("/company", {
			templateUrl : "templates/company.htm"
		})
		.when("/customer", {
			templateUrl : "templates/customer.htm"
		})
		.when("/admin", {
			templateUrl : "templates/admin.htm"
		})
		.when("/showcoupons", {
			templateUrl : "templates/companyTemplates/showCoupons.htm"
		})
		.when("/createcoupon", {
			templateUrl : "templates/companyTemplates/createCoupon.htm"
		})
		.when("/managecoupon", {
			templateUrl : "templates/companyTemplates/manageCoupon.htm"
		})
		.when("/updatecoupon", {
			templateUrl : "templates/companyTemplates/updateCoupon.htm"
		})
		.when("/couponsbycriterias", {
			templateUrl : "templates/companyTemplates/couponsByCriterias.htm"
		})
		.when("/showcouponsbyprice", {
			templateUrl : "templates/companyTemplates/showCouponsByPrice.htm"
		})
		.when("/custpurchasecoupon", {
			templateUrl : "templates/customerTemplates/custPurchaseCoupon.htm"
		})
		.when("/showcustomercoupons", {
			templateUrl : "templates/customerTemplates/showCustomerCoupons.htm"
		})
		.when("/managecustumercoupons", {
			templateUrl : "templates/customerTemplates/manageCustomerCoupons.htm"
		})
		.when("/customercouponsbycriterias", {
			templateUrl : "templates/customerTemplates/customerCouponsByCriterias.htm"
		})
		.when("/showcustomercouponsbyprice", {
			templateUrl : "templates/customerTemplates/customerCouponsByPrice.htm"
		})
		.when("/showcustomercouponsbytype", {
			templateUrl : "templates/customerTemplates/customerCouponsByType.htm"
		})
		.when("/admincompanymanagment", {
			templateUrl : "templates/adminTemplates/adminCompanyManagment.htm"
		})
		.when("/admincustomermanagment", {
			templateUrl : "templates/adminTemplates/adminCustomerManagment.htm"
		})
		.when("/updatecustomer", {
			templateUrl : "templates/adminTemplates/updateCustomer.htm"
		})
		.when("/updatecompany", {
			templateUrl : "templates/adminTemplates/updateCompany.htm"
		})
		.when("/createcustomer", {
			templateUrl : "templates/adminTemplates/createCustomer.htm"
		})
		.when("/createcompany", {
			templateUrl : "templates/adminTemplates/createCompany.htm"
		})
		.when("/showcouponsbytype", {
			templateUrl : "templates/companyTemplates/showCouponsByType.htm"
		});
	}]);


	couponsApp.controller('CouponsController', function($rootScope, $window, UserService, $location){
		
		angular.element(document).ready(function() {
			if (!(UserService.user.isLogged)) {
				$location.path('/');
			}
		});

		$rootScope.view = "/";
		$rootScope.setView = function(view){
			$window.location.assign('#' + view);
		}

	});
	

})();