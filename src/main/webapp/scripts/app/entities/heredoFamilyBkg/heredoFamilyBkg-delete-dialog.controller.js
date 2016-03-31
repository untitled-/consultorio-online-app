'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('HeredoFamilyBkgDeleteController', function($scope, $uibModalInstance, entity, HeredoFamilyBkg) {

        $scope.heredoFamilyBkg = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HeredoFamilyBkg.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
