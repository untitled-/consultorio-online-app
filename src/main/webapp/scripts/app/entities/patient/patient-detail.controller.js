'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('PatientDetailController', function ($scope, $rootScope, $stateParams, entity, Patient, Address, Contact, HeredoFamilyBkg, NonPathologicBkg, PathologicBkg, GynecoobstetricBkg, Consultation) {
        $scope.patient = entity;
        $scope.load = function (id) {
            Patient.get({id: id}, function(result) {
                $scope.patient = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:patientUpdate', function(event, result) {
            $scope.patient = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
