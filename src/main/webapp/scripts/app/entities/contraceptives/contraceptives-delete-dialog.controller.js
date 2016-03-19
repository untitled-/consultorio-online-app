'use strict';

angular.module('consultorioOnlineUiApp')
	.controller('ContraceptivesDeleteController', function($scope, $uibModalInstance, entity, Contraceptives) {

        $scope.contraceptives = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Contraceptives.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
