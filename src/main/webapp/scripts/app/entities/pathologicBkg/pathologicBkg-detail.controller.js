'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('PathologicBkgDetailController', function ($scope, $rootScope, $stateParams, entity, PathologicBkg, Addiction, Alergy, QuirurgicalProcedure, Trauma, Disease, Patient) {
        $scope.pathologicBkg = entity;
        $scope.load = function (id) {
            PathologicBkg.get({id: id}, function(result) {
                $scope.pathologicBkg = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:pathologicBkgUpdate', function(event, result) {
            $scope.pathologicBkg = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
