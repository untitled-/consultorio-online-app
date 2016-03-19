'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('GynecoobstetricBkgDetailController', function ($scope, $rootScope, $stateParams, entity, GynecoobstetricBkg, Contraceptives, Patient) {
        $scope.gynecoobstetricBkg = entity;
        $scope.load = function (id) {
            GynecoobstetricBkg.get({id: id}, function(result) {
                $scope.gynecoobstetricBkg = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:gynecoobstetricBkgUpdate', function(event, result) {
            $scope.gynecoobstetricBkg = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
