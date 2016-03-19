'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('SymptomDetailController', function ($scope, $rootScope, $stateParams, entity, Symptom, Consultation) {
        $scope.symptom = entity;
        $scope.load = function (id) {
            Symptom.get({id: id}, function(result) {
                $scope.symptom = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:symptomUpdate', function(event, result) {
            $scope.symptom = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
