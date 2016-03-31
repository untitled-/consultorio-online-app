'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('PatientDeleteController', function($scope, $uibModalInstance, entity, Patient) {

        $scope.patient = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Patient.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
