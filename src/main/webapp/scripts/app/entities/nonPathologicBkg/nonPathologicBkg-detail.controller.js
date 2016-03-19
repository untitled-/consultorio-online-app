'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('NonPathologicBkgDetailController', function ($scope, $rootScope, $stateParams, entity, NonPathologicBkg, Immunization, Patient) {
        $scope.nonPathologicBkg = entity;
        $scope.load = function (id) {
            NonPathologicBkg.get({id: id}, function(result) {
                $scope.nonPathologicBkg = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:nonPathologicBkgUpdate', function(event, result) {
            $scope.nonPathologicBkg = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
