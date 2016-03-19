'use strict';

angular.module('consultorioOnlineUiApp')
	.controller('QuirurgicalProcedureDeleteController', function($scope, $uibModalInstance, entity, QuirurgicalProcedure) {

        $scope.quirurgicalProcedure = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            QuirurgicalProcedure.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
