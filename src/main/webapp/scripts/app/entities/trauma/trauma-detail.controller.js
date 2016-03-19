'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('TraumaDetailController', function ($scope, $rootScope, $stateParams, entity, Trauma, PathologicBkg) {
        $scope.trauma = entity;
        $scope.load = function (id) {
            Trauma.get({id: id}, function(result) {
                $scope.trauma = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:traumaUpdate', function(event, result) {
            $scope.trauma = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
