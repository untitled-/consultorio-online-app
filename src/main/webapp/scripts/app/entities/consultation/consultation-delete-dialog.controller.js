'use strict';

angular.module('consultorioOnlineUiApp')
	.controller('ConsultationDeleteController', function($scope, $uibModalInstance, entity, Consultation) {

        $scope.consultation = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Consultation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
