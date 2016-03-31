'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('DrugDeleteController', function($scope, $uibModalInstance, entity, Drug) {

        $scope.drug = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Drug.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
