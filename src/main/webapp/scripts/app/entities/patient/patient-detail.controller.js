'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('PatientDetailController', function ($scope, $rootScope, $stateParams, entity, Patient, Address, Contact) {
        $scope.patient = entity;
        $scope.load = function (id) {
            Patient.get({id: id}, function(result) {
                $scope.patient = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:patientUpdate', function(event, result) {
            $scope.patient = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
