'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('AlergyDetailController', function ($scope, $rootScope, $stateParams, entity, Alergy, PathologicBkg) {
        $scope.alergy = entity;
        $scope.load = function (id) {
            Alergy.get({id: id}, function(result) {
                $scope.alergy = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:alergyUpdate', function(event, result) {
            $scope.alergy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
