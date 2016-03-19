'use strict';

angular.module('consultorioOnlineUiApp')
	.controller('TraumaDeleteController', function($scope, $uibModalInstance, entity, Trauma) {

        $scope.trauma = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Trauma.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
