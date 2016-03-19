'use strict';

angular.module('consultorioOnlineUiApp')
	.controller('LabTestDeleteController', function($scope, $uibModalInstance, entity, LabTest) {

        $scope.labTest = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LabTest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
