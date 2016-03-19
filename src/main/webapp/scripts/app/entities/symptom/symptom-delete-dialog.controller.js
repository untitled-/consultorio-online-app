'use strict';

angular.module('consultorioOnlineUiApp')
	.controller('SymptomDeleteController', function($scope, $uibModalInstance, entity, Symptom) {

        $scope.symptom = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Symptom.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
