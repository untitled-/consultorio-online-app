'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        Principal.identity().then(function(account) {
            $scope.account = account;
        });
        
        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });
