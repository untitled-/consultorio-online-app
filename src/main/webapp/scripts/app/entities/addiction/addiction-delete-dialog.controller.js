'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('AddictionDeleteController', function($scope, $uibModalInstance, entity, Addiction) {

        $scope.addiction = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Addiction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
