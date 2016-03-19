'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('ImmunizationDetailController', function ($scope, $rootScope, $stateParams, entity, Immunization, NonPathologicBkg) {
        $scope.immunization = entity;
        $scope.load = function (id) {
            Immunization.get({id: id}, function(result) {
                $scope.immunization = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:immunizationUpdate', function(event, result) {
            $scope.immunization = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
