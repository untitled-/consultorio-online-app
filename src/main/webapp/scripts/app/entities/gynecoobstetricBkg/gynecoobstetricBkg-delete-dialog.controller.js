'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('GynecoobstetricBkgDeleteController', function($scope, $uibModalInstance, entity, GynecoobstetricBkg) {

        $scope.gynecoobstetricBkg = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GynecoobstetricBkg.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
