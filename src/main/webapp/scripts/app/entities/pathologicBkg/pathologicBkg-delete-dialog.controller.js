'use strict';

angular.module('consultorioOnlineAppApp')
	.controller('PathologicBkgDeleteController', function($scope, $uibModalInstance, entity, PathologicBkg) {

        $scope.pathologicBkg = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PathologicBkg.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
