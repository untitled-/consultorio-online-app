'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('ConsultationDetailController', function ($scope, $rootScope, $stateParams, entity, Consultation, LabTest, Treatment, Symptom, Patient) {
        $scope.consultation = entity;
        $scope.load = function (id) {
            Consultation.get({id: id}, function(result) {
                $scope.consultation = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:consultationUpdate', function(event, result) {
            $scope.consultation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
