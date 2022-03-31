(function() {
	var app = angular.module('myApp', [ 'ui.router' ]);

	app.run(function($rootScope, $location, $state, LoginService) {
	});

	app.config([ '$stateProvider','$urlRouterProvider',
			function($stateProvider, $urlRouterProvider) {
				$urlRouterProvider.otherwise('/login');

				$stateProvider.state('login', {
					url : '/login',
					templateUrl : 'loginPage',
					controller : 'LoginController'
				}).state('stock', {
					url : '/stock',
					templateUrl : 'stockPage',
					controller : 'StockController',
					params : {userDetails : ''}
				});
			} ]);

	app.controller('LoginController', function($scope, $rootScope,
			$stateParams, $state, LoginService) {
		$rootScope.title = "Login";

		$scope.formSubmit = function() {

		LoginService.login($scope.username, $scope.password).then(function(data){
			console.log('Login response from server : ' + data);
		    if (data) {
		    	console.log('Login successful');
				$scope.error = '';
				$scope.username = '';
				$scope.password = '';
				console.log('changing state to stock')
				$state.go('stock', {userDetails: data});
			} else {
				console.log('Login failed');
				$scope.error = " Incorrect username/password !";
			}
		})
		};

	});

	app.controller('StockController', function($scope, $rootScope,
			$stateParams, $state, LoginService) {
		$rootScope.title = " Stock Picker ";
		$rootScope.caps = [ "SmallCap", "MidCap", "LargeCap" ];
		$scope.showRecommendedStockTable = false;
		$scope.showloading = false;
		$scope.userId = $stateParams.userDetails.userId;

		$scope.showStockAsPerCap = function() {
			console.log('MarkeCap submit button is clicked');
			$scope.showloading = true;
			$scope.showRecommendedStockTable = false;
			$scope.recommendedStockList = [];
			var requestedCap = $scope.selectedCap;
			console.log('RequestedCap : ' + requestedCap);
			if (undefined != requestedCap) {
				LoginService.getRecommendedStockList(requestedCap).then(function(data){
				console.log('Recommended stock data from server : ' + data)
			    if (data) {
			    	var stockDetails = data;
			    	console.log('stockDetails after recommendation : ' + stockDetails)
			    	$scope.showloading = false;
			    	$scope.recommendedStockList = stockDetails.stocks;
			    	console.log('recommendedStockList for table data : '+ $scope.recommendedStockList)
			    	$scope.showloading = false;
			    	$scope.showRecommendedStockTable = true;
			    }
				})
			}

		};
		$scope.saveStocks = function() {
			console.log('Save stock button is clicked');
			console.log('Current user in save function : '+ $scope.userId);
			var userSelectedStocks = [];
			var totalStocks = $scope.recommendedStockList;
			for (i=0; i<totalStocks.length; i++) {
				if (totalStocks[i].selected == true) {
					quantity = document.getElementById("quantityField" + i).value;
					userSelectedStocks.push({stockName:totalStocks[i].stocksymbol, stocksymbol:totalStocks[i].stocksymbol, stockprice:totalStocks[i].stockprice, stockquantity:quantity});
				}
			}
			console.log('Length of userSelectedStocks : ' + userSelectedStocks.length);
			if (userSelectedStocks.length > 0) {
				console.log('userSelectedStocks : ' + userSelectedStocks);
				LoginService.saveStocksForUser(userSelectedStocks, $scope.userId).then(function(data){
				console.log('saveStocks response from server : ' + data)
			    if (data) {
			    	console.log('Stocks are saved successfully');
			    }
			})
			}
		};

		$scope.getSavedStocks = function() {
			console.log('getSavedStocks button clicked');
			console.log('Current user in getSavedStocks function : '+ $scope.userId);
			console.log('In getSavedStocks function $scope.savedStockList : ' + $scope.savedStockList);
			LoginService.getSavedStockList($scope.userId).then(function(data){
				console.log('getSavedStocks response from server : ' + data)
			    if (data) {
			    	var savedStocks = data;
			    	$scope.savedStockList = savedStocks.stocks;
			    }
			})
		};

		$scope.logout = function() {
			console.log('Logout button clicked');
			$scope.showRecommendedStockTable = false;
			$scope.recommendedStockList = [];
			$scope.savedStockList = [];
			$scope.userId = ""
			console.log('Changing state to login');
			$state.go('login');
		};
	});

	app.factory('LoginService', function($http, $state, $q) {
		var isAuthenticated = false;
		var stockListForSelectedCAP = '';
		var userSavedStockList = '';

		return {
			login : function(username, password) {
				console.log('Inside login function');
				var deferred = $q.defer();
				isAuthenticated = false;
				$http({
					method : 'POST',
					url : 'AuthenticationServlet',
					data : {
						user : username,
						pass : password
					}
				}).success(function(data) {
					console.log('Login successful. Response from server : ' + data);
					isAuthenticated = true;
					deferred.resolve(data);
				}).error(function(msg, code) {
					console.log('Login failed. Response status code : ' + code);
					isAuthenticated = false;
					deferred.resolve(isAuthenticated);
				});
				return deferred.promise;
			},

			getRecommendedStockList : function(requestedCap) {
				console.log('Inside getRecommendedStockList function');
				var deferred = $q.defer();
				$http({
					method : 'GET',
					url : 'RecommendedStockServlet?selectedCap='+requestedCap
				}).success(function(data) {
					console.log('getRecommendedStockList request is successful. Response from server : ' + data);
					stockListForSelectedCAP = data;
					deferred.resolve(stockListForSelectedCAP);
				}).error(function(msg, code) {
					console.log('getRecommendedStockList request is failed. Response status code : ' + code);
					deferred.resolve(false);
				});
				return deferred.promise;
			},

			saveStocksForUser : function(selectedStockArray, userId) {
				console.log('Inside saveStocksForUser function');
				var deferred = $q.defer();
				$http({
					method : 'POST',
					url : 'SaveStockServlet?userId='+userId,
					data : {
						selectedStocks : selectedStockArray
					}
				}).success(function(data) {
					console.log('saveStocksForUser request is successful. Response from server : ' + data);
					deferred.resolve(true);
				}).error(function(msg, code) {
					console.log('saveStocksForUser request is failed. Response status code :  ' + code);
					deferred.resolve(false);
				});
				return deferred.promise;
			},

			getSavedStockList : function(userId) {
				console.log('Inside getSavedStockList funtion');
				var deferred = $q.defer();
				$http({
					method : 'GET',
					url : 'FetchSavedStockServlet?userId='+userId
				}).success(function(data) {
					console.log('getSavedStockList request is successful. Response from server : ' + data);
					userSavedStockList = data;
					deferred.resolve(userSavedStockList);
				}).error(function(msg, code) {
					console.log('getSavedStockList request is failed. Response status code : ' + code);
					deferred.resolve(false);
				});
				return deferred.promise;
			}
				};
			});
})();