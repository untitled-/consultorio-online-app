'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('AddictionDetailController', function ($scope, $rootScope, $stateParams, entity, Addiction, PathologicBkg) {
        $scope.addiction = entity;
        $scope.load = function (id) {
            Addiction.get({id: id}, function(result) {
                $scope.addiction = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:addictionUpdate', function(event, result) {
            $scope.addiction = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
