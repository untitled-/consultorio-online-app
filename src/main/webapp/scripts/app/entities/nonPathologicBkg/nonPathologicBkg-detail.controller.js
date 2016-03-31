'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('NonPathologicBkgDetailController', function ($scope, $rootScope, $stateParams, entity, NonPathologicBkg, Immunization, Patient) {
        $scope.nonPathologicBkg = entity;
        $scope.load = function (id) {
            NonPathologicBkg.get({id: id}, function(result) {
                $scope.nonPathologicBkg = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:nonPathologicBkgUpdate', function(event, result) {
            $scope.nonPathologicBkg = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
