'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('ConsultationDetailController', function ($scope, $rootScope, $stateParams, entity, Consultation, LabTest, Treatment, Symptom, Patient) {
        $scope.consultation = entity;
        $scope.load = function (id) {
            Consultation.get({id: id}, function(result) {
                $scope.consultation = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:consultationUpdate', function(event, result) {
            $scope.consultation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
