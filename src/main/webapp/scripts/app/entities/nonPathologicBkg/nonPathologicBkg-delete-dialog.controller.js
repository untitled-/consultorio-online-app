'use strict';

angular.module('consultorioOnlineUiApp')
	.controller('NonPathologicBkgDeleteController', function($scope, $uibModalInstance, entity, NonPathologicBkg) {

        $scope.nonPathologicBkg = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            NonPathologicBkg.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
