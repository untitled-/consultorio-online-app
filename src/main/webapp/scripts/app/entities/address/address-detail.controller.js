'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('AddressDetailController', function ($scope, $rootScope, $stateParams, entity, Address, Patient) {
        $scope.address = entity;
        $scope.load = function (id) {
            Address.get({id: id}, function(result) {
                $scope.address = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:addressUpdate', function(event, result) {
            $scope.address = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
