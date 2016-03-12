'use strict';

angular.module('consultorioOnlineUiApp').controller('AddressDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Address', 'Patient',
        function($scope, $stateParams, $uibModalInstance, entity, Address, Patient) {

        $scope.address = entity;
        $scope.patients = Patient.query();
        $scope.load = function(id) {
            Address.get({id : id}, function(result) {
                $scope.address = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:addressUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.address.id != null) {
                Address.update($scope.address, onSaveSuccess, onSaveError);
            } else {
                Address.save($scope.address, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
