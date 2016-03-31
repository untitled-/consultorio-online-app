'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('AlergyDeleteController', function($scope, $uibModalInstance, entity, Alergy) {

        $scope.alergy = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Alergy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
