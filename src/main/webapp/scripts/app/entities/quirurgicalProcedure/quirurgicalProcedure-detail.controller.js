'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('QuirurgicalProcedureDetailController', function ($scope, $rootScope, $stateParams, entity, QuirurgicalProcedure, PathologicBkg) {
        $scope.quirurgicalProcedure = entity;
        $scope.load = function (id) {
            QuirurgicalProcedure.get({id: id}, function(result) {
                $scope.quirurgicalProcedure = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:quirurgicalProcedureUpdate', function(event, result) {
            $scope.quirurgicalProcedure = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
