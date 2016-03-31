'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('DiseaseDeleteController', function($scope, $uibModalInstance, entity, Disease) {

        $scope.disease = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Disease.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
