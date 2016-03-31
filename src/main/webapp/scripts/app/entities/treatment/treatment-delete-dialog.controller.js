'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('TreatmentDeleteController', function($scope, $uibModalInstance, entity, Treatment) {

        $scope.treatment = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Treatment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
