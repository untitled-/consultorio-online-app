'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('TraumaDetailController', function ($scope, $rootScope, $stateParams, entity, Trauma, PathologicBkg) {
        $scope.trauma = entity;
        $scope.load = function (id) {
            Trauma.get({id: id}, function(result) {
                $scope.trauma = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:traumaUpdate', function(event, result) {
            $scope.trauma = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
