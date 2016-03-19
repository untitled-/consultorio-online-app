'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('DiseaseDetailController', function ($scope, $rootScope, $stateParams, entity, Disease, HeredoFamilyBkg, PathologicBkg) {
        $scope.disease = entity;
        $scope.load = function (id) {
            Disease.get({id: id}, function(result) {
                $scope.disease = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:diseaseUpdate', function(event, result) {
            $scope.disease = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
