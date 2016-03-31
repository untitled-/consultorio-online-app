'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('ImmunizationDeleteController', function($scope, $uibModalInstance, entity, Immunization) {

        $scope.immunization = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Immunization.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
