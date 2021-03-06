'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('DrugDetailController', function ($scope, $rootScope, $stateParams, entity, Drug, Treatment) {
        $scope.drug = entity;
        $scope.load = function (id) {
            Drug.get({id: id}, function(result) {
                $scope.drug = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:drugUpdate', function(event, result) {
            $scope.drug = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
