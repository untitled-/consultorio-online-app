'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('HeredoFamilyBkgDetailController', function ($scope, $rootScope, $stateParams, entity, HeredoFamilyBkg, Disease, Patient) {
        $scope.heredoFamilyBkg = entity;
        $scope.load = function (id) {
            HeredoFamilyBkg.get({id: id}, function(result) {
                $scope.heredoFamilyBkg = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:heredoFamilyBkgUpdate', function(event, result) {
            $scope.heredoFamilyBkg = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
