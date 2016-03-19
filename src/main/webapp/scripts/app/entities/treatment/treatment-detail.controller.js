'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('TreatmentDetailController', function ($scope, $rootScope, $stateParams, entity, Treatment, Drug, Consultation) {
        $scope.treatment = entity;
        $scope.load = function (id) {
            Treatment.get({id: id}, function(result) {
                $scope.treatment = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:treatmentUpdate', function(event, result) {
            $scope.treatment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
