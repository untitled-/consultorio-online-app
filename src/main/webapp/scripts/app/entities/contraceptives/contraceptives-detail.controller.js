'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('ContraceptivesDetailController', function ($scope, $rootScope, $stateParams, entity, Contraceptives, GynecoobstetricBkg) {
        $scope.contraceptives = entity;
        $scope.load = function (id) {
            Contraceptives.get({id: id}, function(result) {
                $scope.contraceptives = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:contraceptivesUpdate', function(event, result) {
            $scope.contraceptives = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
