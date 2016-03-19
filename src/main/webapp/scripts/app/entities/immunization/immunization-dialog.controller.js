'use strict';

angular.module('consultorioOnlineUiApp').controller('ImmunizationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Immunization', 'NonPathologicBkg',
        function($scope, $stateParams, $uibModalInstance, entity, Immunization, NonPathologicBkg) {

        $scope.immunization = entity;
        $scope.nonpathologicbkgs = NonPathologicBkg.query();
        $scope.load = function(id) {
            Immunization.get({id : id}, function(result) {
                $scope.immunization = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:immunizationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.immunization.id != null) {
                Immunization.update($scope.immunization, onSaveSuccess, onSaveError);
            } else {
                Immunization.save($scope.immunization, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
